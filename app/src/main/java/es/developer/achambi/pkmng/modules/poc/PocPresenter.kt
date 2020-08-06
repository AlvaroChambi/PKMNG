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
        val END_NODE = 214
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

      /*  repeat(10) {
            val speed = pokemons[it].speed
            if(!speeds.contains(speed)) {
                speeds.add(speed)
            }
        }*/

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


        val vertexSet = ArrayList<Node>()
        val result = Path()
        shortestPath(matrix, END_NODE, vertexSet, result)
        result.toString()
       // printMatrix(matrix)
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

    fun shortestPath( matrix: ArrayList<ArrayList<Int>>, end: Int, vertexSet: ArrayList<Node>, result: Path ) {
        //populate vertex set //vertex set should be sorted, but maybe not here, but when values are updated, here i want the source node
        //to be the first one
        repeat(matrix.size) {
            vertexSet.add(Node(id = it))
        }
        //pop highest value node: first one should be the source
        val currentNode = vertexSet[0]
        vertexSet.remove(currentNode)

        //check if we got to the destination and finish
        if(currentNode.id == end) {
            var setCurrent = vertexSet[currentNode.id]

            do {
                result.path.add(setCurrent.id)
                result.totalWeight = setCurrent.value
                setCurrent = vertexSet[setCurrent.previousId]
            }while(setCurrent.previousId != UNDEFINED)
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
        vertexSet.sortBy { it.value }//This may be more efficient?¿
    }
}

class Path(val path: ArrayList<Int> = ArrayList(), var totalWeight: Int = 0)

data class Node(val id: Int = 0, public var value: Int = 0, var previousId: Int = PocPresenter.UNDEFINED)