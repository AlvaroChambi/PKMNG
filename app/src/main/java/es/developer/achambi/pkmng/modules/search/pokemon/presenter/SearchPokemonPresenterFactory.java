package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.ISearchPokemonScreen;

public class SearchPokemonPresenterFactory implements ISearchPokemonPresenterFactory {
    private IPokemonDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public SearchPokemonPresenterFactory( IPokemonDataAccessFactory dataAccessFactory,
                                          MainExecutor executor ) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    @Override
    public SearchPokemonPresenter buildPresenter( ISearchPokemonScreen screen ) {
        return new SearchPokemonPresenter( screen, dataAccessFactory.buildDataAccess(), executor );
    }
}
