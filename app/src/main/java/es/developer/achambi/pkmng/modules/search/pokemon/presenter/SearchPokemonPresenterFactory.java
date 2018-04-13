package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.view.ISearchPokemonScreen;

public class SearchPokemonPresenterFactory implements ISearchPokemonPresenterFactory {
    private IPokemonDataAccessFactory dataAccessFactory;

    public SearchPokemonPresenterFactory( IPokemonDataAccessFactory dataAccessFactory ) {
        this.dataAccessFactory = dataAccessFactory;
    }

    @Override
    public SearchPokemonPresenter buildPresenter( ISearchPokemonScreen screen ) {
        return new SearchPokemonPresenter( screen, dataAccessFactory.buildDataAccess() );
    }
}
