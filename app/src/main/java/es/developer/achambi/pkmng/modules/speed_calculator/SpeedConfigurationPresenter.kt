package es.developer.achambi.pkmng.modules.speed_calculator

import es.developer.achambi.coreframework.threading.*
import es.developer.achambi.coreframework.ui.Presenter
import es.developer.achambi.pkmng.modules.overview.model.Pokemon
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess

class SpeedConfigurationPresenter(private val screen: SpeedConfigurationScreen,
    executor: MainExecutor,
    private val dataAccess: IPokemonDataAccess): Presenter(screen, executor) {


    fun fetchPokemons() {
        val handler = object : ResponseHandler<ArrayList<Pokemon>>() {
            override fun onSuccess(response: Response<ArrayList<Pokemon>>?) {
                screen.pokemonDataReceived(response!!.data)
            }

            override fun onError(error: Error?) {
                super.onError(error)
                screen.pokemonDataError()
            }
        }

        //TODO why this is allowing me to use a lambda and no the other interfaces I've made?
        val task = Request<ArrayList<Pokemon>> { Response(dataAccess.accessData()) }
        request(task, handler)
    }
}