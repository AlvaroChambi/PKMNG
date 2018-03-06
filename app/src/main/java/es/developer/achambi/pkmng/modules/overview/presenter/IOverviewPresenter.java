package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenter;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenter;

public interface IOverviewPresenter extends ViewPresenter {
    void onQueryTextSubmit(String query);
    void onQueryTextChanged(String query);
    ArrayList<Pokemon> fetchPokemonList();
    ArrayList<PokemonConfig> fetchConfigurationList();

    ArrayList<Pokemon> getPokemonList();
    ArrayList<PokemonConfig> getConfigurationList();

    void onConfigurationCreated( PokemonConfig config );
    void onConfigurationUpdated( PokemonConfig config );

    SearchPokemonPresenter getPokemonPresenter();
    SearchConfigurationPresenter getConfigurationPresenter();
}
