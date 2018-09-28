package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.ArrayList;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenter;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenter;

public abstract class IOverviewPresenter extends Presenter {
    public IOverviewPresenter(Screen screen, MainExecutor executor) {
        super(screen, executor);
    }

    public abstract void onQueryTextSubmit(String query);
    public abstract void onQueryTextChanged(String query);

    public abstract ArrayList<Pokemon> getPokemonList();
    public abstract ArrayList<PokemonConfig> getConfigurationList();

    public abstract void onConfigurationCreated( PokemonConfig config );
    public abstract void onConfigurationUpdated( PokemonConfig config );

    public abstract SearchPokemonPresenter getPokemonPresenter();
    public abstract SearchConfigurationPresenter getConfigurationPresenter();
}
