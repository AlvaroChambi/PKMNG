package es.developer.achambi.pkmng.modules.overview.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.overview.screen.IOverviewScreen;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.ISearchPokemonPresenterFactory;

public class OverviewPresenterFactory implements IOverviewPresenterFactory{
    private ISearchPokemonPresenterFactory pokemonPresenterFactory;
    private MainExecutor executor;

    public OverviewPresenterFactory( ISearchPokemonPresenterFactory pokemonPresenterFactory,
                                     MainExecutor executor ) {
        this.pokemonPresenterFactory = pokemonPresenterFactory;
        this.executor = executor;
    }

    @Override
    public OverviewPresenter buildPresenter(IOverviewScreen screen) {
        return new OverviewPresenter(screen, pokemonPresenterFactory.buildPresenter(screen),
                executor);
    }
}
