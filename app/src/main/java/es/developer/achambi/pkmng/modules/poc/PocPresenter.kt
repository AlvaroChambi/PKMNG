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

    fun test() {}

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

    fun performQuery() {
        val evs = ArrayList<Int>()
        val ivs = ArrayList<Int>()
        for (value: Int in 0..62) {
            evs.add(value)
        }
        for (value: Int in 0..30) {
            ivs.add(value)
        }
        val baseStats = ArrayList<Pair<Int, Int>>()
        val speeds = ArrayList<Int>()

        pokemons.forEach {
            baseStats.add(Pair(it.id, it.speed))
            if(!speeds.contains(it.speed)) {
                speeds.add(it.speed)
            }
        }

        val matrix = ArrayList<ArrayList<Int>>()

        for( i in 0..speeds.size) {
            matrix.add(ArrayList())
        }

        for(i in 0..speeds.size) {
            for(j in 0..speeds.size) {
                matrix[i].add(0)
            }
        }

        for(i in 1..speeds.size) {
            matrix[0][i] = speeds[i - 1]
        }

        evs.forEach {
            matrix.add(ArrayList())
            repeat(matrix.size) {
                matrix[matrix.size - 1].add(0)
            }
            matrix.forEach{
                it.add(0)
            }
        }

/*        evs.forEach { ev ->
            repeat(speeds.size) {
                matrix[it + 1][matrix.size - 1] = ev
            }
        }*/

        /*repeat(ivs.size) {
            matrix.add(ArrayList())
            repeat(matrix.size) {
                matrix[matrix.size - 1].add(0)
            }
            matrix.forEach {
                it.add(0)
            }
        }*/



        Log.i("POC", printMatrix(matrix))

    }

    fun addEmptyNode(matrix: ArrayList<ArrayList<Int>>) {
        matrix.add(ArrayList())
        matrix.forEach {
            it.add(0)
        }
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
        for(pos in 0..speeds.size) {
            linkRootToNode(matrix, speeds[pos], pos)
        }
    }

    fun linkRootToNode(matrix: ArrayList<ArrayList<Int>>, value: Int, position: Int) {
        //root wont link to itself, but with every other added node
        val jList = matrix[0]
        jList[position + 1] = value
    }

    fun printMatrix(matrix: ArrayList<ArrayList<Int>>): String {
        var result = ""
        matrix.forEach {
            it.forEach {value ->
                result += "$value,"
            }
            result += '\n'
        }
        return result
    }
}