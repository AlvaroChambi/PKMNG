package es.developer.achambi.pkmng.modules.poc

import android.util.Log
import es.developer.achambi.coreframework.threading.*
import es.developer.achambi.coreframework.ui.Presenter
import es.developer.achambi.pkmng.modules.overview.model.Pokemon
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccess
import es.developer.achambi.pkmng.modules.search.nature.model.Nature
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess

class PocPresenter(val executor: MainExecutor, val screen: PocScreen,
                   val pokemonDataAccess: PokemonDataAccess,
                   val natureDataAccess: NatureDataAccess): Presenter(screen, executor) {
    private lateinit var pokemons: ArrayList<Pokemon>
    private lateinit var natures: ArrayList<Nature>

    companion object {
        val UNDEFINED = -1
        val END_NODE = 214 //only valid for the graph containing base speeds, evs and ivs
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

    fun buildMatrix() {
        val speeds = ArrayList<Int>()

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

        //Link to end
        val evsOffset = speedsOffset + evs.size
        addEmptyNode(matrix)
        repeat(ivs.size) {pos ->
            linkPosToNewNode(matrix, offset = evsOffset, position = pos,
                    value = 1)
        }


        val result = Path()
        shortestPath(matrix, END_NODE, result)
        //Lets cast this to something I can understand

        //path will include 3 nodes, ivs, evs, base_speed:

        //first we get the iv's step, because is the last one that we added
       val rawIv = result.path[0]
       val actualIVIndex = rawIv - evsOffset
       val iv = ivs[actualIVIndex]

        val rawEv = result.path[1]
        val  actualEVIndex = rawEv - speedsOffset
        val ev = evs[actualEVIndex]

        val rawBaseStat = result.path[2]
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

        screen.showBestPathResult(pokemonString, ev = ev, iv= iv)
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

    fun shortestPath( matrix: ArrayList<ArrayList<Int>>, end: Int, result: Path ) {
        val vertexSet = ArrayList<Node>()
        val visited = ArrayList<Node>()
        //populate vertex set //vertex set should be sorted, but maybe not here, but when values are updated, here i want the source node
        //to be the first one
        repeat(matrix.size) {
            vertexSet.add(Node(id = it))
        }


        while(vertexSet.isNotEmpty()) {
            //pop highest value node: first one should be the source
            val currentNode = vertexSet[0]
            vertexSet.remove(currentNode)
            visited.add(currentNode)

            //check if we got to the destination and finish
            if(currentNode.id == end) {
                var setCurrent = visited.find { currentNode.id == it.id }!! //another iteration, but it should be over a rly small list

                do {
                    setCurrent = visited.find { setCurrent.previousId == it.id }!!
                    result.path.add(setCurrent.id)
                    result.totalWeight = setCurrent.value
                }while(setCurrent.previousId != UNDEFINED)
                return
            }

            //iterate each neighbour
            repeat(matrix.size) {pos -> //position should be the node id
                val neighbourWeight = matrix[pos][currentNode.id] //current node id should be it's position on the matrix
                if(neighbourWeight != 0) { //neighbour will be the vertex weight
                    //found valid neighbour
                    val neighbourNode = vertexSet.find { it.id == pos }!! //vertex set iteration
                    //calculate new value and check if it's better than the previous
                    val newValue = currentNode.value + neighbourWeight
                    if(newValue > currentNode.value) {
                        neighbourNode.value = newValue
                        neighbourNode.previousId = currentNode.id
                    }
                }
            }
            //Need to sort, but putting the highest values first
            vertexSet.sortByDescending { it.value }//This may be more efficient?¿
        }
    }

    fun yens(matrix: ArrayList<ArrayList<Int>>, source: Int, sink: Int, iterations: Int) {
        val initialPath = Path()
        shortestPath(matrix, sink, initialPath)

        val bestPaths = ArrayList<Path>()
        bestPaths.add(initialPath)
        val potentialPaths = ArrayList<Path>()

        for(iteration in 1..iterations) {
            //iterate each spur node of the previous best path( initial on the first iteration )
            //spur will be each path nodes from first to the next to last
            val currentPath = bestPaths[iteration - 1]
            for(spurPosition in 0..currentPath.path.size - 2){
                //using the spur node find the spur path  (from 0 to spurNode)
                val spurNode = currentPath.path[spurPosition]
                val rootPath = ArrayList<Int>()
                var index = 0
                while(rootPath[index++] != spurNode) { //not checking index out of bound, it should never happen
                    rootPath.add(currentPath.path[index])
                }
                //Then for each previous path, remove edges when needed to avoid
                //choosing always the same best paths
                bestPaths.forEach {
                    //here I have to remove edges from the graph, but I should be able to restore the graph later
                    //ideas: remember what i've removed ?, but how can I exactly remove an edge?

                    //So I'll have two nodes: A and B
                    //And I have to remove the edge linking those, basically
                    //matrix[A][B] == //it's a directed graph, so not really sure if it's matrix[B][A]
                    // = 0  -> But first I have to save the weight value and the A and B index

                    //for the nodes, how can I remove a node :( basically collapsing every row/column into the disappearing one
                    //And of course save every link value on some kind of array to be able to add this in it's previous place :(
                }
            }
        }
    }
}

class Path(val path: ArrayList<Int> = ArrayList(), var totalWeight: Int = 0)

class Edge(val start: Int, val end: Int,  val weight: Int)

class Graph(private val matrix: ArrayList<ArrayList<Int>>,
            private var removed: ArrayList<Int>,
            private var removedEdges: ArrayList<Edge>) {
    fun removeNode(node: Int) {
        removed.add(node)
    }

    fun removeEdge(start: Int, end: Int) {
        val weight = matrix[start][end]
        removedEdges.add(Edge(start, end, weight))
    }

    fun restore() {
        removed.clear()
        removedEdges.forEach {
            matrix[it.start][it.end] = it.weight
        }
        removedEdges.clear()
    }

    fun getAdjacencyList(node: Int): ArrayList<Int> {
        val result = ArrayList<Int>()
        var position = 0
        repeat(matrix.size) {
            if(!removed.contains(it)) {
                val neighbourWeight = matrix[position++][node]
            }
        }
        return result
    }

    fun getAllNodes(): ArrayList<Int> {
        var position = 0
        val result = ArrayList<Int>()
        repeat(matrix.size) {
            if(!removed.contains(it)) {
                result.add(position++)
            }
        }
        return result
    }
}

data class Node(val id: Int = 0, public var value: Int = 0, var previousId: Int = PocPresenter.UNDEFINED)