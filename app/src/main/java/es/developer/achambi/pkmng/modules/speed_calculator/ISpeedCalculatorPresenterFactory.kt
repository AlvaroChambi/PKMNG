package es.developer.achambi.pkmng.modules.speed_calculator

import es.developer.achambi.coreframework.threading.MainExecutor
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess

interface ISpeedCalculatorPresenterFactory {
    fun buildPresenter(screen: SpeedConfigurationScreen): SpeedConfigurationPresenter
}

class SpeedCalculatorFactory(private val dataAccess: IPokemonDataAccess, private val executor: MainExecutor)
    : ISpeedCalculatorPresenterFactory {
    override fun buildPresenter(screen: SpeedConfigurationScreen): SpeedConfigurationPresenter {
        return SpeedConfigurationPresenter(screen, executor, dataAccess)
    }

}