package es.developer.achambi.pkmng.modules.poc

import android.util.Log
import es.developer.achambi.coreframework.threading.*
import es.developer.achambi.coreframework.ui.Presenter
import es.developer.achambi.pkmng.modules.overview.model.Pokemon
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccess
import es.developer.achambi.pkmng.modules.search.nature.model.Nature
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess
import es.developer.achambi.pkmng.modules.utils.PokemonUtils
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.Math.floor

class PocPresenter(val executor: MainExecutor, val screen: PocScreen,
                   val pokemonDataAccess: PokemonDataAccess,
                   val natureDataAccess: NatureDataAccess): Presenter(screen, executor) {
    private lateinit var pokemons: ArrayList<Pokemon>
    private lateinit var natures: ArrayList<Nature>

    companion object {
        val UNDEFINED = -1
        val NATURE_NEUTRAL_KEY = 1000
        val NATURE_POSITIVE_KEY = 2000
        val NATURE_NEGATIVE_KEY = 3000
    }

    fun onViewSetup(){
        val handler = object : ResponseHandler<ArrayList<Pokemon>>() {
            override fun onSuccess(response: Response<ArrayList<Pokemon>>?) {
                pokemons = response?.data!!
                pokemons.sortBy { it.speed }
                response.data?.size?.let { screen.showNumberOfPokemons(it) }
            }

            override fun onError(error: Error?) {
                super.onError(error)
                screen.showNumberOfPokemons(0)
            }
        }

        val request = Request<ArrayList<Pokemon>> { Response(pokemonDataAccess.accessData()) }
        request(request, handler)

        val natureHandler = object : ResponseHandler<ArrayList<Nature>>(){
            override fun onSuccess(response: Response<ArrayList<Nature>>?) {
                natures = response?.data!!
                screen.showNumberOfNature(natures.size)
            }

            override fun onError(error: Error?) {
                super.onError(error)
                screen.showNumberOfNature(0)
            }
        }

        val natureRequest = Request<ArrayList<Nature>>{ Response((natureDataAccess.accessData())) }
        request(natureRequest, natureHandler)
    }

    fun addEmptyNode(matrix: ArrayList<ArrayList<Int>>) {
        //Create new column
        val matrixSize = matrix.size
        matrix.add(ArrayList())

        //fill up new column to match previous size
        repeat(matrixSize){
            matrix[matrixSize].add(0)
        }

        //fill up new row
        matrix.forEach {
            it.add(0)
        }
    }


    /**
     * Links node in position to the new added node: position will be adjusted with the offset
     */
    fun linkPosToNewNode(matrix: ArrayList<ArrayList<Int>>, offset: Int,position: Int, value: Int) {
        //move to the last created node, then set required position to the required value.
        matrix[matrix.size - 1][position + offset] = value
    }

    fun startQuery(iterations: Int, configTarget: Int) {
        val handler = object : ResponseHandler<ArrayList<Item>>(){
            override fun onSuccess(response: Response<ArrayList<Item>>) {

                screen.showYenResults(response.data)
            }
        }
        request(
                Request<ArrayList<Item>> { Response(buildMatrix(iterations, configTarget))},
                handler
        )
    }

    fun buildMatrix(iterations: Int, configTarget: Int):ArrayList<Item> {
        val speeds = ArrayList<Int>()
        val natures = ArrayList<Int>()
        natures.add(NATURE_NEUTRAL_KEY)
        natures.add(NATURE_POSITIVE_KEY)
        natures.add(NATURE_NEGATIVE_KEY)

        pokemons.forEach {
            if(!speeds.contains(it.speed)) {
                speeds.add(it.speed)
            }
        }


        val matrix = ArrayList<ArrayList<Int>>()
        //add root node to matrix
        addEmptyNode(matrix)

        //add first step nodes
        repeat(speeds.size) {
            addEmptyNode(matrix)
        }

        //link nodes to root
        repeat(speeds.size) {pos ->
            linkRootToNode(matrix, speeds[pos], pos)
        }




        val evs = ArrayList<Int>()
        val ivs = ArrayList<Int>()
        for (value: Int in 1..63) {
            evs.add(value)
        }
        for (value: Int in 1..31) {
            ivs.add(value)
        }
        val rootOffset = 1
        //Link speed nodes to the ev nodes
        //Each speed node will be linked to every ev node
        evs.forEach {
            //create node for each IV
            addEmptyNode(matrix)
            repeat(speeds.size) { pos ->
                //Link previous nodes to that node
                linkPosToNewNode(matrix, offset = rootOffset,
                        position = pos,value =  it)
            }

        }



       //Link every ev to the new Iv's nodes
        val speedsOffset = rootOffset + speeds.size
        ivs.forEach {
            addEmptyNode(matrix)
            repeat(evs.size) { pos ->
                linkPosToNewNode(matrix, offset = speedsOffset, position = pos,
                        value = it)
            }
        }

        //link every iv to nature nodes
        val evsOffset = speedsOffset + evs.size
        natures.forEach {
            addEmptyNode(matrix)
            repeat(ivs.size) { pos ->
                linkPosToNewNode(matrix, offset = evsOffset, position = pos,
                value = it)
            }
        }

        //Link to end
        val ivsOffset = evsOffset + ivs.size
        addEmptyNode(matrix)
        repeat(natures.size) {pos ->
            linkPosToNewNode(matrix, offset = ivsOffset, position = pos,
                    value = 1)
        }

        Log.i("YEN", "starting yen...")
        val start = System.currentTimeMillis()
        val resultList = yens(Graph(matrix = matrix), matrix.size - 1, iterations = iterations,
                target = configTarget)
        Log.i("YEN", "found: " + resultList.size + " results" )
        Log.i("YEN", "time spent: " + (System.currentTimeMillis() - start))

        //Lets cast this to something I can understand

        //path will include 3 nodes, ivs, evs, base_speed:

        //first we get the iv's step, because is the last one that we added
        val items = ArrayList<Item>()
        var targetPosition = 0
        var found = false

        resultList.forEach { result ->
            Log.i("YEN", "path size: " + result.path.size)
            val rawNature = result.path[4]
            val natureMultiplier : Float
            val nature = when(natures[rawNature - ivsOffset]) {
                NATURE_NEUTRAL_KEY -> {
                    natureMultiplier = 1f
                    "neutral"
                }
                NATURE_POSITIVE_KEY -> {
                    natureMultiplier = 1.1f
                    "positive"
                }
                NATURE_NEGATIVE_KEY -> {
                    natureMultiplier = 0.9f
                    "negative"
                }
                else -> {
                    natureMultiplier = 1f
                    "undefined"
                }
            }

            val rawIv = result.path[3]
            val actualIVIndex = rawIv - evsOffset
            val iv = ivs[actualIVIndex]

            val rawEv = result.path[2]
            val  actualEVIndex = rawEv - speedsOffset
            val ev = evs[actualEVIndex]// * 4 //cast to actual ev value

            val rawBaseStat = result.path[1]
            val actualBaseIndex = rawBaseStat - rootOffset
            val baseSpeed = speeds[actualBaseIndex]

            val pokemonList = ArrayList<Pokemon>()
            var pokemonString = ""
            pokemons.forEach {
                if(it.stats.speed == baseSpeed) {
                    pokemonList.add(it)
                    pokemonString += it.name + ", "
                }
            }
            pokemonString += ": value $baseSpeed"
            val total = PokemonUtils.getStatValue(baseSpeed, ev, natureMultiplier,50,
            iv)
            val item = Item(pokemon = pokemonString, ev = ev.toString(), iv = iv.toString(),
                    total = total.toString(), nature = nature, weight = result.totalWeight.toString())

            items.add(item)

            if(configTarget == result.totalWeight) {
                targetPosition = items.size - 1
                found = true
            }
        }
        if(targetPosition == 0) {
            targetPosition = items.size - 1
        }
        return items
    }

    /**
     * Input node value to link the root to and position where it should go:
     * position will be adjusted to never link 0 with itself
     */
    fun linkRootToNode(matrix: ArrayList<ArrayList<Int>>, value: Int, position: Int) {
        //root wont link to itself, but with every other added node
        val jList = matrix[position + 1]
        jList[0] = value
    }

    fun printMatrix(matrix: ArrayList<ArrayList<Int>>): String {
        var result = ""
        for( i in 0..matrix.size - 1) {
            for( j in 0..matrix.size - 1 ) {
                val value = matrix[j][i]
                result += "$value,"
            }
            result += '\n'
        }
        return result
    }

    fun dijkstraTarget( graph: Graph,start: Int, end: Int, result: Path, target: Int ) {
        val vertexSet = ArrayList<Node>()
        val visited = ArrayList<Node>()
        //populate vertex set //vertex set should be sorted, but maybe not here, but when values are updated, here i want the source node
        //to be the first one
        val nodes = graph.getAllNodes()
        vertexSet.add(Node(start))
        nodes.remove(start) //already added to the vertex, remove to avoid adding it again

        nodes.forEach {
            vertexSet.add(Node(id = it))
        }

        while(vertexSet.isNotEmpty()) {
            //pop the best value node(it's sorted)
            val currentNode = vertexSet[0]
            vertexSet.remove(currentNode)
            visited.add(currentNode)

            //check if we got to the destination and finish
            if(vertexSet.isEmpty()) {
                //setting a ceiling, we can find no way to get to the end point
                var setCurrent = visited.find { end == it.id }!! //another iteration, but it should be over a rly small list
                result.totalWeight = setCurrent.value

                result.path.add(setCurrent.id)

                if(setCurrent.previousId == UNDEFINED) {
                    result.totalWeight = 0
                    result.path.clear()
                    return
                }

                do {
                    setCurrent = visited.find { setCurrent.previousId == it.id }!!
                    result.path.add(setCurrent.id)
                }while(setCurrent.previousId != UNDEFINED)
                result.path.reverse()
                //let's just omit the result if [0] is not source
                if(result.path[0] == start) {
                    return
                } else {
                    result.totalWeight = 0
                    result.path.clear()
                    return
                }
            }

            //iterate each neighbour
            graph.getAdjacencyList(currentNode.id).forEach {pos -> //position should be the node id
                val neighbourWeight = graph.matrix[pos][currentNode.id] //current node id should be it's position on the matrix
                //found valid neighbour: Valid means that it's not on the visited list!
                var neighbourNode: Node? = null
                repeat(vertexSet.size) {
                    val node = vertexSet[it]
                    if(node.id == pos) {
                        neighbourNode = node
                    }
                }

                if(neighbourNode != null) { //Test this thing, when I am getting this value?
                    //calculate new value and check if it's better than the previous
                    val newValue = when(neighbourWeight) {
                        NATURE_NEUTRAL_KEY -> currentNode.value
                        NATURE_POSITIVE_KEY -> kotlin.math.floor(currentNode.value * 1.1).toInt()  //round down if decimal
                        NATURE_NEGATIVE_KEY -> {
                            kotlin.math.floor(currentNode.value * 0.9).toInt() }
                        else -> currentNode.value + neighbourWeight
                    }

                    //here I'd need to stop if I get a weight higher than the target
                    // let's keep it simple, if higher, just ignore it
                    if(newValue <= target && newValue >= currentNode.value) {
                        neighbourNode?.value = newValue
                        neighbourNode?.previousId = currentNode.id
                    }
                }


            }
            //Need to sort, but putting the highest values first
            vertexSet.sortByDescending { it.value }//This may be more efficient?¿
        }
    }

    fun shortestPath( graph: Graph,start: Int, end: Int, result: Path ) {
        val vertexSet = ArrayList<Node>()
        val visited = ArrayList<Node>()
        //populate vertex set //vertex set should be sorted, but maybe not here, but when values are updated, here i want the source node
        //to be the first one
        val nodes = graph.getAllNodes()
        vertexSet.add(Node(start))
        nodes.remove(start) //already added to the vertex, remove to avoid adding it again

        nodes.forEach {
            vertexSet.add(Node(id = it))
        }

        while(vertexSet.isNotEmpty()) {
            //pop the best value node(it's sorted)
            val currentNode = vertexSet[0]
            vertexSet.remove(currentNode)
            visited.add(currentNode)

            //check if we got to the destination and finish
            if(currentNode.id == end) {
                var setCurrent = visited.find { currentNode.id == it.id }!! //another iteration, but it should be over a rly small list
                    result.totalWeight = setCurrent.value

                    result.path.add(setCurrent.id)
                do {
                    setCurrent = visited.find { setCurrent.previousId == it.id }!!
                    result.path.add(setCurrent.id)
                }while(setCurrent.previousId != UNDEFINED)
                result.path.reverse()
                //let's just omit the result if [0] is not source
                if(result.path[0] == start) {
                    return
                } else {
                    result.totalWeight = 0
                    result.path.clear()
                    return
                }
            }

            //iterate each neighbour
            graph.getAdjacencyList(currentNode.id).forEach {pos -> //position should be the node id
                val neighbourWeight = graph.matrix[pos][currentNode.id] //current node id should be it's position on the matrix
                //found valid neighbour: Valid means that it's not on the visited list!
                var neighbourNode: Node? = null
                vertexSet.forEach {
                    if(it.id == pos) {
                        neighbourNode = it
                    }
                }
                if(neighbourNode != null) { //Test this thing, when I am getting this value?
                    //calculate new value and check if it's better than the previous
                    val newValue = when(neighbourWeight) {
                        NATURE_NEUTRAL_KEY -> currentNode.value
                        NATURE_POSITIVE_KEY -> kotlin.math.floor(currentNode.value * 1.1).toInt()  //round down if decimal
                        NATURE_NEGATIVE_KEY -> kotlin.math.floor(currentNode.value * 0.9).toInt()
                        else -> currentNode.value + neighbourWeight
                    }

                    if(newValue > currentNode.value) {
                        neighbourNode?.value = newValue
                        neighbourNode?.previousId = currentNode.id
                    }
                }


            }
            //Need to sort, but putting the highest values first
            vertexSet.sortByDescending { it.value }//This may be more efficient?¿
        }
    }

    fun yens(graph: Graph, sink: Int, iterations: Int, target: Int): ArrayList<Path> {
        val initialPath = Path()
        bellmanFord(graph, 0, sink, initialPath, target)

        val bestPaths = ArrayList<Path>()
        bestPaths.add(initialPath)
        val potentialPaths = ArrayList<Path>()

        for(iteration in 1..iterations) {
            //iterate each spur node of the previous best path( initial on the first iteration )
            //spur will be each path nodes from first to the next to last
            val currentPath = bestPaths[iteration - 1]
            for(spurPosition in 0..currentPath.path.size - 2){

                val spurNode = currentPath.path[spurPosition]
                val rootPath = ArrayList<Int>()
                val completeRootPath = Path(rootPath)
                //using the spur node find the spur path  (from 0 to spurNode) including the spur node
                //adding nodes from the current best path to the rootPath

                //test this shit
                loop@ for(it in 0..currentPath.path.size-1) {
                    rootPath.add(currentPath.path[it])

                    //get to the end of rootPath, check if something before, add it to the weight
                    if(it > 0  && it == rootPath.size - 1) {
                        val weight = graph.matrix[currentPath.path[it]][currentPath.path[it - 1]]
                        if(weight == 0) {
                            throw Exception("corrupted matrix: missing edge: " + (it-1) + "->" +it)
                        }
                        completeRootPath.totalWeight+= weight
                    }
                    if(currentPath.path[it] == spurNode) {
                        break@loop
                    }
                }

                //Then for each previous path, remove edges when needed to avoid
                //choosing always the same best paths

                //first remove edges
                bestPaths.forEach {
                    if(rootPath == it.path.subList(0, spurPosition + 1)) {
                        //remove path that are part of the previous shortest path which share the same root path
                        val start = it.path[spurPosition]
                        val end = it.path[spurPosition + 1]
                        if(graph.matrix[end][start] != 0) { //check if the edge has not been removed yet //should this happen?
                            graph.removeEdge(start, end)
                        }
                    }
                }

                //Remove nodes in root path except the spur node
                rootPath.forEach {
                    if(it != spurNode) {
                        graph.removeNode(it)
                    }
                }

                val spurPath = Path()
                bellmanFord(graph, spurNode, sink, spurPath, target - completeRootPath.totalWeight)
                if(spurPath.path.isNotEmpty()) {
                    //Get total path from the rootPath + the spurPath
                    val pathList = ArrayList<Int>()
                    val totalPath = Path(pathList)

                    rootPath.forEach {
                        pathList.add(it)
                    }
                    spurPath.path.forEach {
                        if(!pathList.contains(it)) {
                            pathList.add(it)
                        }
                    }

                    //Getting new weight
                    //we need, every path value to calculate the new weight, that is
                    // path[0] = root
                    //  path[1] -> speed_stat: id,  path[2]  -> ev: id,  path[3] -> iv: id, path[4] -> nature: id
                    // for each id I need to get the actual weight value, that'll be the weights for
                    //      speed               ev              iv            nature
                    // root -> speedId,  speedId -> evId, evId -> ivId,  ivID -> nature
                    val speedId = pathList[1]
                    val evId = pathList[2]
                    val ivID = pathList[3]
                    val natureId = pathList[4]

                    val speedStat = graph.matrix[speedId][0]
                    val evStat = graph.matrix[evId][speedId]
                    val ivStat = graph.matrix[ivID][evId]
                    val natureMultiplier = when(graph.matrix[natureId][ivID]) {
                        NATURE_NEUTRAL_KEY -> Nature.NEUTRAL_STAT_MODIFIER
                        NATURE_POSITIVE_KEY -> Nature.INCREASED_STAT_MODIFIER
                        NATURE_NEGATIVE_KEY -> Nature.DECREASED_STAT_MODIFIER
                        else -> Nature.NEUTRAL_STAT_MODIFIER
                    }
                    totalPath.totalWeight = PokemonUtils.getStatValue(speedStat, evStat,
                    natureMultiplier, 50, ivStat)

                    if (!potentialPaths.contains(totalPath)) {
                        potentialPaths.add(totalPath)
                    }
                } else {
                    val a: Int
                }

                graph.restore()
            }
            if(potentialPaths.isEmpty()) {
                return bestPaths
            }

            var bestValue = 0
            var bestPath = Path()
            var key = -1
            repeat(potentialPaths.size) {
                val path = potentialPaths[it]
                if(path.totalWeight > bestValue) { //if there's any potential path this will always get in
                    bestValue = path.totalWeight
                    bestPath = path
                    key = it
                }
            }
            if(key == -1) throw Exception("How the hell we didn't get any best path?")
            bestPaths.add(bestPath)

            potentialPaths.removeAt(key)
        }
        return bestPaths
    }

    fun bellmanFord(graph: Graph,start: Int, end: Int, result: Path, target: Int) {
        val nodes = ArrayList<Node>()
        graph.getAllNodes().forEach {
            nodes.add(Node(it))
        }

        loop@ for(it in 0 .. nodes.size - 2) {
            val relaxed = ArrayList<Boolean>()
            repeat(nodes.size) {
                relaxed.add(false)
            }
            nodes.forEach { sourceNode ->
                graph.getAdjacencyList(sourceNode.id).forEach { neighbourId->
                    val neighbourNode = nodes.find { neighbourId == it.id }!!

                    val newWeight = when(val linkWeight = graph.matrix[neighbourId][sourceNode.id]) {
                        NATURE_NEUTRAL_KEY -> sourceNode.value
                        NATURE_POSITIVE_KEY -> kotlin.math.floor(sourceNode.value * 1.1).toInt()  //round down if decimal
                        NATURE_NEGATIVE_KEY -> kotlin.math.floor(sourceNode.value * 0.9).toInt()
                        else -> sourceNode.value + linkWeight
                    }

                    if( newWeight > neighbourNode.value && newWeight <= target) {
                        try {
                            relaxed[neighbourId] = true
                        } catch (e: Exception){}

                        neighbourNode.value = newWeight
                        neighbourNode.previousId = sourceNode.id
                    }
                }
            }
            val somethingChanged = relaxed.find { it }
            if(somethingChanged == null) {
                break@loop
            }
        }

        var currentNode = nodes.find { it.id == end }!!
        result.path.add(end)
        result.totalWeight = currentNode.value
        while(currentNode.previousId != UNDEFINED) {
            result.path.add(currentNode.previousId)
            currentNode = nodes.find { currentNode.previousId == it.id }!!
        }
        if(currentNode.id == start) {
            result.path.reverse()
            return
        } else {
            result.path.clear()
            result.totalWeight = 0
            return
        }
    }
}

data class Path(val path: ArrayList<Int> = ArrayList(), var totalWeight: Int = 0)

class Edge(val start: Int, val end: Int,  val weight: Int)

class Graph( val matrix: ArrayList<ArrayList<Int>>,
            private var removed: ArrayList<Int> = ArrayList()
             , private var removedEdges: ArrayList<Edge> = ArrayList()
            ) {
    fun removeNode(node: Int) {
        removed.add(node)
    }

    fun removeEdge(start: Int, end: Int) {
        removedEdges.forEach {
            if(it.start == start && it.end == end ) {
                throw IllegalArgumentException("Edge: " + start + " - " +end + " already removed")
            }
        }
        val weight = matrix[end][start]
        matrix[end][start] = 0
        removedEdges.add(Edge(start, end, weight))
    }

    fun restore() {
        removed.clear()
        removedEdges.forEach {
            matrix[it.end][it.start] = it.weight
        }
        removedEdges.clear()
    }

    @Throws
    fun getAdjacencyList(node: Int): ArrayList<Int> {
        if(removed.contains(node)) {
            throw Exception("node is removed, neighbours shouldn't be requested")
        }
        val result = ArrayList<Int>()
        repeat(matrix.size) {
            if(!removed.contains(it)) {
                val neighbourWeight = matrix[it][node]
                if(neighbourWeight != 0) {
                    result.add(it)
                }
            }
        }
        return result
    }

    fun getAllNodes(): ArrayList<Int> {
        val result = ArrayList<Int>()
        repeat(matrix.size) {
            if(!removed.contains(it)) {
                result.add(it)
            }
        }
        return result
    }
}

data class Node(val id: Int = 0, public var value: Int = 0, var previousId: Int = PocPresenter.UNDEFINED)