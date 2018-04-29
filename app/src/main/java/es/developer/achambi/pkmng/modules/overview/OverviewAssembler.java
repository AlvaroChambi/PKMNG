package es.developer.achambi.pkmng.modules.overview;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.search.configuration.SearchConfigurationAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.SearchPokemonAssembler;

public class OverviewAssembler {
    private MainExecutor executor;
    private SearchPokemonAssembler searchPokemonAssembler;
    private SearchConfigurationAssembler searchConfigurationAssembler;

    public OverviewAssembler setSearchPokemonAssembler(SearchPokemonAssembler searchPokemonAssembler) {
        this.searchPokemonAssembler = searchPokemonAssembler;
        return this;
    }

    public OverviewAssembler setSearchConfigurationAssembler(
            SearchConfigurationAssembler searchConfigurationAssembler) {
        this.searchConfigurationAssembler = searchConfigurationAssembler;
        return this;
    }

    public OverviewAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public IOverviewPresenterFactory getPresenterFactory() {
        return new OverviewPresenterFactory( searchPokemonAssembler.getPresenterFactory(),
                searchConfigurationAssembler.getPresenterFactory(), executor );
    }
}
