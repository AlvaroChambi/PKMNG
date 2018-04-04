package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenter;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenter;

public class OverviewPresenter extends IOverviewPresenter {
    private static final String TAG = OverviewPresenter.class.getCanonicalName();

    private SearchPokemonPresenter pokemonPresenter;
    private SearchConfigurationPresenter configurationPresenter;

    public OverviewPresenter(IOverviewView view, Screen screen) {
        super(screen);
        configurationPresenter = new SearchConfigurationPresenter( view, screen );
        pokemonPresenter = new SearchPokemonPresenter( view, screen );
    }

    @Override
    public void onQueryTextSubmit(String query) {
        Log.i(TAG, "query text submitted: " + query);
    }

    @Override
    public void onQueryTextChanged(String query) {
        Log.i(TAG, "query text changed: " + query);
    }

    public void fetchPokemonList( ResponseHandler<ArrayList<Pokemon>> responseHandler ) {
        pokemonPresenter.fetchPokemonList( responseHandler );
    }

    public void fetchConfigurationList(
            ResponseHandler<ArrayList<PokemonConfig>> responseHandler ) {
        configurationPresenter.fetchConfigurationList( responseHandler );
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
