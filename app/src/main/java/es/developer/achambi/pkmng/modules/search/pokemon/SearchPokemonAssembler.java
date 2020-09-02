package es.developer.achambi.pkmng.modules.search.pokemon;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.PokemonDataAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.ISearchPokemonPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenterFactory;
import es.developer.achambi.pkmng.modules.speed_calculator.ISpeedCalculatorPresenterFactory;
import es.developer.achambi.pkmng.modules.speed_calculator.SpeedCalculatorFactory;

public class SearchPokemonAssembler {
    private MainExecutor mainExecutor;
    private PokemonDataAssembler pokemonDataAssembler;

    public SearchPokemonAssembler setMainExecutor(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
        return this;
    }

    public SearchPokemonAssembler setPokemonDataAssembler(
            PokemonDataAssembler pokemonDataAssembler) {
        this.pokemonDataAssembler = pokemonDataAssembler;
        return this;
    }

    public ISearchPokemonPresenterFactory getPresenterFactory() {
        return new SearchPokemonPresenterFactory(pokemonDataAssembler.getPokemonDataAccess(),
                mainExecutor);
    }

    public ISpeedCalculatorPresenterFactory getSpeedCalculatorFactory() {
        return new SpeedCalculatorFactory(pokemonDataAssembler.getPokemonDataAccess(), mainExecutor);
    }
}
