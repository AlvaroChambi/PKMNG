package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public interface IOverviewPresenter extends ViewPresenter {
    void onQueryTextSubmit(String query);
    void onQueryTextChanged(String query);
    ArrayList<Pokemon> fetchPokemonList();
    ArrayList<PokemonConfig> fetchConfigurationList();

    ArrayList<Pokemon> getPokemonList();
    ArrayList<PokemonConfig> getConfigurationList();

    OverviewPresenter.OnPokemonClickedListener providePokemonListener();
    OverviewPresenter.OnConfigurationClickedListener provideConfigurationListener();
}
