package es.developer.achambi.pkmng.modules.search.pokemon;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.PokemonDataAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.ISearchPokemonPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenterFactory;

public class SearchPokemonAssembler {
    private MainExecutor mainExecutor;
    public PokemonDataAssembler pokemonDataAssembler;

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
}
