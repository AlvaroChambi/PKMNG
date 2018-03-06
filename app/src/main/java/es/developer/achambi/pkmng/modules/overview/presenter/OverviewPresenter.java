package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenter;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenter;

public class OverviewPresenter implements IOverviewPresenter {
    private static final String TAG = OverviewPresenter.class.getCanonicalName();

    private SearchPokemonPresenter pokemonPresenter;
    private SearchConfigurationPresenter configurationPresenter;

    public OverviewPresenter(IOverviewView view) {
        configurationPresenter = new SearchConfigurationPresenter( view );
        pokemonPresenter = new SearchPokemonPresenter( view );
    }

    @Override
    public void onQueryTextSubmit(String query) {
        Log.i(TAG, "query text submitted: " + query);
    }

    @Override
    public void onQueryTextChanged(String query) {
        Log.i(TAG, "query text changed: " + query);
    }

    @Override
    public ArrayList<Pokemon> fetchPokemonList() {
        return pokemonPresenter.fetchPokemonList();
    }

    @Override
    public ArrayList<PokemonConfig> fetchConfigurationList() {
        return configurationPresenter.fetchConfigurationList();
    }

    @Override
    public ArrayList<Pokemon> getPokemonList() {
        return pokemonPresenter.getPokemonList();
    }

    @Override
    public ArrayList<PokemonConfig> getConfigurationList() {
        return configurationPresenter.getConfigurationList();
    }

    @Override
    public void onConfigurationCreated(PokemonConfig config) {
        configurationPresenter.getConfigurationList().add( config );
    }

    @Override
    public void onConfigurationUpdated(PokemonConfig config) {
        int indexToReplace = configurationPresenter.getConfigurationList().indexOf( config );
        configurationPresenter.getConfigurationList().set( indexToReplace, config );
    }

    public SearchPokemonPresenter getPokemonPresenter() {
        return pokemonPresenter;
    }

    @Override
    public SearchConfigurationPresenter getConfigurationPresenter() {
        return configurationPresenter;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        pokemonPresenter.onSaveInstanceState( bundle );
        configurationPresenter.onSaveInstanceState( bundle );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        pokemonPresenter.onRestoreInstanceState( bundle );
        configurationPresenter.onRestoreInstanceState( bundle );
    }
}
