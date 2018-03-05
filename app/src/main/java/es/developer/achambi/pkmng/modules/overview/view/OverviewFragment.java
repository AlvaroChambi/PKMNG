package es.developer.achambi.pkmng.modules.overview.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSearchAdapter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSuggestionsAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationPresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.PokemonPresentation;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.viewholder.ConfigurationViewHolder;
import es.developer.achambi.pkmng.modules.overview.view.viewholder.PokemonViewHolder;

public class OverviewFragment extends BaseSearchListFragment implements IOverviewView {
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CONFIGURATION_DETAILS_DIALOG_TAG = "CONFIGURATION_DETAILS_DIALOG_TAG";
    private static final String SEARCH_FILTER_ARGUMENT_KEY = "SEARCH_FILTER_ARGUMENT_KEY";
    private static final String USE_CONTEXT_ARGUMENT_KEY = "USE_CONTEXT_ARGUMENT_KEY";
    private static final String CURRENT_POKEMON_ARGUMENT_KEY = "CURRENT_POKEMON_ARGUMENT_KEY";
    private static final String CURRENT_CONFIGURATION_ARGUMENT_KEY =
            "CURRENT_CONFIGURATION_ARGUMENT_KEY";

    private static final int CREATE_CONFIGURATION_REQUEST_CODE = 101;
    private static final int UPDATE_CONFIGURATION_REQUEST_CODE = 102;

    private IOverviewPresenter presenter;
    private ArrayList<PokemonPresentation> pokemonList;
    private ArrayList<OverviewConfigurationPresentation> configurationList;
    private PokemonPresentation pokemon;
    private OverviewConfigurationPresentation configuration;

    private SearchFilter searchFilter;

    public enum UseContext{
        OVERVIEW_SEARCH_CONTEXT,
        REPLACE_SEARCH_CONTEXT
    }

    public static OverviewFragment newInstance( Bundle args ) {
        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle getFragmentArgs( SearchFilter searchFilter, UseContext useContext,
                                          Pokemon pokemon ) {
        Bundle args = new Bundle();
        args.putInt( SEARCH_FILTER_ARGUMENT_KEY, searchFilter.ordinal() );
        args.putInt( USE_CONTEXT_ARGUMENT_KEY, useContext.ordinal() );
        args.putParcelable( CURRENT_POKEMON_ARGUMENT_KEY, pokemon );
        return args;
    }

    public static Bundle getFragmentArgs( SearchFilter searchFilter, UseContext useContext,
                                          PokemonConfig configuration ) {
        Bundle args = new Bundle();
        args.putInt( SEARCH_FILTER_ARGUMENT_KEY, searchFilter.ordinal() );
        args.putInt( USE_CONTEXT_ARGUMENT_KEY, useContext.ordinal() );
        args.putParcelable( CURRENT_CONFIGURATION_ARGUMENT_KEY, configuration );
        return args;
    }

    @Override
    public int getHeaderLayoutResource() {
        switch ( searchFilter ) {
            case POKEMON_FILTER:
                return  R.layout.pokemon_list_item_header_layout;
            case CONFIGURATION_FILTER:
                return R.layout.pokemon_config_list_item_header_layout;
            case ALL_FILTER:
            default:
                return super.getHeaderLayoutResource();
        }
    }

    @Override
    public void onHeaderSetup(View header) {
        switch ( searchFilter ) {
            case POKEMON_FILTER:
                if( !pokemon.empty ) {
                    PokemonViewHolder headerHolder = new PokemonViewHolder( header );
                    headerHolder.linkTo( header );
                    headerHolder.bindTo( pokemon );
                } else {
                    header.setVisibility(View.GONE);
                }
                break;
            case CONFIGURATION_FILTER:
                if( !configuration.empty ) {
                    ConfigurationViewHolder configurationHolder =
                            new ConfigurationViewHolder( header );
                    configurationHolder.linkTo( header );
                    configurationHolder.bindTo( configuration );
                } else {
                    header.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchFilter = SearchFilter.ALL_FILTER;
        if( getArguments()!= null ) {
            searchFilter =
                    SearchFilter.values()[getArguments().getInt( SEARCH_FILTER_ARGUMENT_KEY )];
        }

        switch ( searchFilter ) {
            case POKEMON_FILTER:
                pokemon = PokemonPresentation.Builder.buildPresentation( getActivity(),
                        ((Pokemon)getArguments().getParcelable( CURRENT_POKEMON_ARGUMENT_KEY ))
                );
                break;
            case CONFIGURATION_FILTER:
                configuration = OverviewConfigurationPresentation.Builder
                        .buildPresentation( getActivity(),
                        ((PokemonConfig) getArguments()
                                .getParcelable( CURRENT_CONFIGURATION_ARGUMENT_KEY )) );
                break;
        }
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
        adapter.setListener( presenter.providePokemonListener() );
        ConfigurationSearchAdapter configurationAdapter = new ConfigurationSearchAdapter(
                configurationList );
        configurationAdapter.setListener( presenter.provideConfigurationListener() );
        ConfigurationSearchAdapter fullAdapter =
                new ConfigurationSearchAdapter( configurationList, adapter );
        fullAdapter.setListener( presenter.provideConfigurationListener() );

        switch ( searchFilter ) {
            case POKEMON_FILTER:
                return adapter;
            case CONFIGURATION_FILTER:
                return  configurationAdapter;
            case ALL_FILTER:
                return fullAdapter;
            default:
                return fullAdapter;
        }
    }

    @Override
    public void doRequest() {
        pokemonList = PresentationBuilder.buildPokemonPresentation(
                        getActivity(), presenter.fetchPokemonList() );
        configurationList = PresentationBuilder.buildConfigurationPresentation(
                        getActivity(), presenter.fetchConfigurationList() );

        refreshAdapter();
    }

    private UseContext getUseContext() {
        if( getArguments() != null ) {
            return UseContext.values()[getArguments().getInt( USE_CONTEXT_ARGUMENT_KEY )];
        }

        return UseContext.OVERVIEW_SEARCH_CONTEXT;
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
                PokemonDetailsFragment.newInstance( pokemon, getUseContext() );
        detailsFragment.setTargetFragment( this, CREATE_CONFIGURATION_REQUEST_CODE );
        detailsFragment.show( transaction, POKEMON_DETAILS_DIALOG_TAG );
    }

    @Override
    public void showConfigurationDetails(PokemonConfig configuration) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ConfigurationDetailsFragment configDetails = ConfigurationDetailsFragment.newInstance(
                configuration, getUseContext() );
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

    public class ConfigurationSearchAdapter extends SearchAdapterDecorator<OverviewConfigurationPresentation,
            ConfigurationViewHolder> {

        public ConfigurationSearchAdapter( ArrayList data ) {
            super(data);
        }

        public ConfigurationSearchAdapter(ArrayList data, SearchAdapterDecorator adapter) {
            super(data, adapter);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.pokemon_config_list_cardview_item_layout;
        }

        @Override
        public int getAdapterViewType() {
            return R.id.pokemon_configuration_view_id;
        }

        @Override
        public ConfigurationViewHolder createViewHolder(View rootView ) {
            final ConfigurationViewHolder viewHolder = new ConfigurationViewHolder(rootView);
            viewHolder.linkTo( rootView );
            return viewHolder;
        }

        @Override
        public void bindViewHolder( ConfigurationViewHolder holder,
                                    OverviewConfigurationPresentation configuration ) {
            holder.bindTo( configuration );
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
        public static ArrayList<OverviewConfigurationPresentation> buildConfigurationPresentation(
                Context context, ArrayList<PokemonConfig> configurations ) {
            ArrayList<OverviewConfigurationPresentation> presentations = new ArrayList<>();
            for( PokemonConfig configuration : configurations ) {
                presentations.add( OverviewConfigurationPresentation.Builder
                        .buildPresentation( context, configuration ) );
            }

            return presentations;
        }
    }
}
