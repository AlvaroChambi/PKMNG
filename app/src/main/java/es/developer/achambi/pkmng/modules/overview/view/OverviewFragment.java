package es.developer.achambi.pkmng.modules.overview.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.details.view.ConfigurationDetailsFragment;
import es.developer.achambi.pkmng.modules.details.view.DetailsUseContext;
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.search.pokemon.adapter.PokemonSearchAdapter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSuggestionsAdapter;
import es.developer.achambi.pkmng.modules.search.configuration.view.presentation.ConfigurationPresentation;
import es.developer.achambi.pkmng.modules.search.pokemon.view.presentation.PokemonPresentation;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.configuration.adapter.SearchConfigurationAdapter;

public class OverviewFragment extends BaseSearchListFragment implements IOverviewView {
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CONFIGURATION_DETAILS_DIALOG_TAG = "CONFIGURATION_DETAILS_DIALOG_TAG";

    private static final int CREATE_CONFIGURATION_REQUEST_CODE = 101;
    private static final int UPDATE_CONFIGURATION_REQUEST_CODE = 102;

    private IOverviewPresenter presenter;
    private ArrayList<PokemonPresentation> pokemonList;
    private ArrayList<ConfigurationPresentation> configurationList;

    public static OverviewFragment newInstance( Bundle args ) {
        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewSetup(View view, Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if(!isViewRecreated() && savedInstanceState == null) {
            doRequest();
        } else {
            refreshAdapter();
        }
    }

    @Override
    public ViewPresenter setupPresenter() {
        if(presenter == null) {
            presenter = new OverviewPresenter(this);
        }
        return presenter;
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        PokemonSearchAdapter adapter = new PokemonSearchAdapter( pokemonList );
        adapter.setListener( presenter.getPokemonPresenter() );
        SearchConfigurationAdapter configurationAdapter = new SearchConfigurationAdapter(
                configurationList );
        configurationAdapter.setListener( presenter.getConfigurationPresenter() );
        SearchConfigurationAdapter fullAdapter =
                new SearchConfigurationAdapter( configurationList, adapter );
        fullAdapter.setListener( presenter.getConfigurationPresenter() );

        return fullAdapter;
    }

    @Override
    public void doRequest() {
        pokemonList = PresentationBuilder.buildPokemonPresentation  (
                        getActivity(), presenter.fetchPokemonList() );
        configurationList = PresentationBuilder.buildConfigurationPresentation(
                        getActivity(), presenter.fetchConfigurationList() );

        refreshAdapter();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pokemonList = PresentationBuilder.buildPokemonPresentation(
                getActivity(), presenter.getPokemonList());
        configurationList = PresentationBuilder.buildConfigurationPresentation(
                getActivity(), presenter.getConfigurationList());
    }

    @Override
    public void showPokemonDetails(Pokemon pokemon) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PokemonDetailsFragment detailsFragment =
                PokemonDetailsFragment.newInstance( pokemon, DetailsUseContext.SELECT_CONTEXT);
        detailsFragment.setTargetFragment( this, CREATE_CONFIGURATION_REQUEST_CODE );
        detailsFragment.show( transaction, POKEMON_DETAILS_DIALOG_TAG );
    }

    @Override
    public void showConfigurationDetails(PokemonConfig configuration) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ConfigurationDetailsFragment configDetails = ConfigurationDetailsFragment.newInstance(
                configuration, DetailsUseContext.SELECT_CONTEXT );
        configDetails.setTargetFragment( this, UPDATE_CONFIGURATION_REQUEST_CODE );
        configDetails.show( transaction, CONFIGURATION_DETAILS_DIALOG_TAG );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.overview_search_action).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_pokemon_hint));
        final PokemonSuggestionsAdapter pokemonCursorAdapter = new PokemonSuggestionsAdapter(
                getActivity());
        searchView.setSuggestionsAdapter(pokemonCursorAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(pokemonCursorAdapter.getValue(position), false);
                return true;
            }
        });

        //TODO Maybe some basic custom FilterSearchView: can translate this events to something more precise
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onQueryTextSubmit(query);
                searchView.clearFocus(); //hide soft keyboard
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onQueryTextChanged(newText);
                pokemonCursorAdapter.onQueryTextChanged(newText);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == Activity.RESULT_OK && requestCode == CREATE_CONFIGURATION_REQUEST_CODE ) {
            PokemonConfig pokemonConfig = data.getParcelableExtra(
                    ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY );

            presenter.onConfigurationCreated(pokemonConfig);

            configurationList = PresentationBuilder.buildConfigurationPresentation(
                    getActivity(), presenter.getConfigurationList() );
            refreshAdapter();
        } else if( resultCode == Activity.RESULT_OK &&
                requestCode == UPDATE_CONFIGURATION_REQUEST_CODE ) {
            PokemonConfig pokemonConfig = data.getParcelableExtra(
                    ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY );
            presenter.onConfigurationUpdated(pokemonConfig);

            configurationList = PresentationBuilder.buildConfigurationPresentation(
                    getActivity(), presenter.getConfigurationList() );
            refreshAdapter();
        }
    }

    private static class PresentationBuilder {
        @NotNull
        public static ArrayList<PokemonPresentation> buildPokemonPresentation(
                Context context, ArrayList<Pokemon> pokemonList ) {
            ArrayList<PokemonPresentation> presentations = new ArrayList<>();
            for( Pokemon pokemon : pokemonList ) {
                presentations.add( PokemonPresentation.Builder
                        .buildPresentation( context, pokemon ) );
            }

            return presentations;
        }

        @NotNull
        public static ArrayList<ConfigurationPresentation> buildConfigurationPresentation(
                Context context, ArrayList<PokemonConfig> configurations ) {
            ArrayList<ConfigurationPresentation> presentations = new ArrayList<>();
            for( PokemonConfig configuration : configurations ) {
                presentations.add( ConfigurationPresentation.Builder
                        .buildPresentation( context, configuration ) );
            }

            return presentations;
        }
    }
}
