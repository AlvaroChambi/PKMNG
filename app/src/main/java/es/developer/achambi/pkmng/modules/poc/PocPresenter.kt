package es.developer.achambi.pkmng.modules.poc

import android.util.Log
import es.developer.achambi.coreframework.threading.*
import es.developer.achambi.coreframework.ui.Presenter
import es.developer.achambi.pkmng.modules.overview.model.Pokemon
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccess
import es.developer.achambi.pkmng.modules.search.nature.model.Nature
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess
import java.lang.Exception
import java.lang.IllegalArgumentException

class PocPresenter(val executor: MainExecutor, val screen: PocScreen,
                   val pokemonDataAccess: PokemonDataAccess,
                   val natureDataAccess: NatureDataAccess): Presenter(screen, executor) {
    private lateinit var pokemons: ArrayList<Pokemon>
    private lateinit var natures: ArrayList<Nature>

    companion object {
        val UNDEFINED = -1
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

    fun buildMatrix(iterations: Int, targetValue: Int) {
        val speeds = ArrayList<Int>()

        pokemons.forEach {
            if(!speeds.contains(it.speed) &&  targetValue + 10> it.speed && it.speed> targetValue - 10) {
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

        //Link to end
        val evsOffset = speedsOffset + evs.size
        addEmptyNode(matrix)
        repeat(ivs.size) {pos ->
            linkPosToNewNode(matrix, offset = evsOffset, position = pos,
                    value = 1)
        }

        val start = System.currentTimeMillis()
        val resultList = yens(Graph(matrix = matrix), matrix.size - 1, iterations)
        Log.i("YEN", "found: " + resultList.size + " results" )
        Log.i("YEN", "time spent: " + (System.currentTimeMillis() - start))

        //Lets cast this to something I can understand

        //path will include 3 nodes, ivs, evs, base_speed:

        //first we get the iv's step, because is the last one that we added
        val items = ArrayList<Item>()
        resultList.forEach { result ->
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
            items.add(Item(pokemon = pokemonString, ev = ev.toString(), iv = iv.toString(),
            total = (result.totalWeight - 1).toString())) //adjust for the value added to reach the sink
        }
        screen.showYenResults(items)
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
            //how the hell do we check if the path starts at the source?:
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
                    val newValue = currentNode.value + neighbourWeight
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

    fun yens(graph: Graph, sink: Int, iterations: Int): ArrayList<Path> {
        val initialPath = Path()
        shortestPath(graph, 0, sink, initialPath)


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
                        //graph.removeEdge(start, end)
                    }
                }

                //Remove nodes in root path except the spur node
                rootPath.forEach {
                    if(it != spurNode) {
                        graph.removeNode(it)
                    }
                }

                val spurPath = Path()
                shortestPath(graph, spurNode, sink, spurPath)
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
                    totalPath.totalWeight = completeRootPath.totalWeight + spurPath.totalWeight
                    //Now we need the weight value of linking both paths, that'll be the edge between the last node of the root path
                    //and the first one on the spur path. If it's the same node, weight will be 0, so don't need to worry about that
                    val linkStart = rootPath[rootPath.size - 1]
                    val linkEnd = spurPath.path[0]
                    totalPath.totalWeight += graph.matrix[linkEnd][linkStart]


                    if (!potentialPaths.contains(totalPath)) {
                        potentialPaths.add(totalPath)
                    }
                }

                graph.restore()
            }
            if(potentialPaths.isEmpty())  return bestPaths

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