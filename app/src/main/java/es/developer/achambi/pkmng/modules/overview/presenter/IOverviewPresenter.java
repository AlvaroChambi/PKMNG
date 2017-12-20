package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.List;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;

public interface IOverviewPresenter extends ViewPresenter {
    void onQueryTextSubmit(String query);
    void onQueryTextChanged(String query);
    List<Pokemon> fetchPokemonList();
    List<PokemonConfig> fetchConfigurationList();
    OverviewPresenter.OnPokemonClickedListener providePokemonListener();
    OverviewPresenter.OnConfigurationClickedListener provideConfigurationListener();
}
