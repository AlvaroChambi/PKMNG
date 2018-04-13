package es.developer.achambi.pkmng.modules.overview.presenter;

import es.developer.achambi.pkmng.modules.overview.view.IOverviewScreen;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.ISearchPokemonPresenterFactory;

public class OverviewPresenterFactory implements IOverviewPresenterFactory{
    private ISearchPokemonPresenterFactory pokemonPresenterFactory;

    public OverviewPresenterFactory( ISearchPokemonPresenterFactory pokemonPresenterFactory ) {
        this.pokemonPresenterFactory = pokemonPresenterFactory;
    }

    @Override
    public OverviewPresenter buildPresenter(IOverviewScreen screen) {
        return new OverviewPresenter(screen, pokemonPresenterFactory.buildPresenter(screen) );
    }
}
