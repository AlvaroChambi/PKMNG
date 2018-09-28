package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.ISearchPokemonScreen;

public class SearchPokemonPresenterFactory implements ISearchPokemonPresenterFactory {
    private IPokemonDataAccess dataAccess;
    private MainExecutor executor;

    public SearchPokemonPresenterFactory( IPokemonDataAccess dataAccess,
                                          MainExecutor executor ) {
        this.dataAccess = dataAccess;
        this.executor = executor;
    }

    @Override
    public SearchPokemonPresenter buildPresenter( ISearchPokemonScreen screen ) {
        return new SearchPokemonPresenter( screen, dataAccess, executor );
    }
}
