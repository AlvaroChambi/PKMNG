package es.developer.achambi.pkmng.modules.overview.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.overview.screen.IOverviewScreen;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.ISearchConfigurationPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.ISearchPokemonPresenterFactory;

public class OverviewPresenterFactory implements IOverviewPresenterFactory{
    private ISearchPokemonPresenterFactory pokemonPresenterFactory;
    private ISearchConfigurationPresenterFactory configPresenterFactory;
    private MainExecutor executor;

    public OverviewPresenterFactory(ISearchPokemonPresenterFactory pokemonPresenterFactory,
                                    ISearchConfigurationPresenterFactory configPresenterFactory,
                                    MainExecutor executor) {
        this.pokemonPresenterFactory = pokemonPresenterFactory;
        this.configPresenterFactory = configPresenterFactory;
        this.executor = executor;
    }

    @Override
    public OverviewPresenter buildPresenter(IOverviewScreen screen) {
        return new OverviewPresenter(screen,
                pokemonPresenterFactory.buildPresenter(screen),
                configPresenterFactory.buildPresenter(screen),
                executor);
    }
}
