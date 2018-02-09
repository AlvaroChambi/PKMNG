package es.developer.achambi.pkmng.modules.overview.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;
import es.developer.achambi.pkmng.core.ui.view.TypeView;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.details.view.ConfigurationDetailsFragment;
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSuggestionsAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewViewDataBuilder;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class OverviewFragment extends BaseSearchListFragment implements IOverviewView {
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CONFIGURATION_DETAILS_DIALOG_TAG = "CONFIGURATION_DETAILS_DIALOG_TAG";
    private static final String SEARCH_FILTER_ARGUMENT_KEY = "SEARCH_FILTER_ARGUMENT_KEY";
    private static final String USE_CONTEXT_ARGUMENT_KEY = "USE_CONTEXT_ARGUMENT_KEY";

    private static final int CREATE_CONFIGURATION_REQUEST_CODE = 101;
    private static final int UPDATE_CONFIGURATION_REQUEST_CODE = 102;

    private IOverviewPresenter presenter;
    private ArrayList<OverviewPokemonRepresentation> pokemonList;
    private ArrayList<OverviewConfigurationRepresentation> configurationList;

    public enum UseContext{
        OVERVIEW_SEARCH_CONTEXT,
        REPLACE_SEARCH_CONTEXT;
    }

    public static OverviewFragment newInstance( Bundle args ) {
        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle getFragmentArgs( SearchFilter searchFilter, UseContext useContext ) {
        Bundle args = new Bundle();
        args.putInt( SEARCH_FILTER_ARGUMENT_KEY, searchFilter.ordinal() );
        args.putInt( USE_CONTEXT_ARGUMENT_KEY, useContext.ordinal() );
        return args;
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

        switch ( getSearchFilter() ) {
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
    public void onViewSetup(View view, Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if(!isViewRecreated() && savedInstanceState == null) {
            doRequest();
        } else {
            refreshAdapter();
        }
    }

    @Override
    public void doRequest() {
        OverviewViewDataBuilder dataBuilder = new OverviewViewDataBuilder();
        pokemonList = dataBuilder.buildPokemonPresentation(
                        getActivity(), presenter.fetchPokemonList() );
        configurationList = dataBuilder.buildConfigurationPresentation(
                        getActivity(), presenter.fetchConfigurationList() );

        refreshAdapter();
    }

    /**
     * Returns all if no search filter is found
     * @return
     */
    private SearchFilter getSearchFilter() {
        if( getArguments()!= null ) {
            return SearchFilter.values()[getArguments().getInt( SEARCH_FILTER_ARGUMENT_KEY )];
        }

        return SearchFilter.ALL_FILTER;
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
        pokemonList = new OverviewViewDataBuilder().buildPokemonPresentation(
                getActivity(), presenter.getPokemonList());
        configurationList = new OverviewViewDataBuilder().buildConfigurationPresentation(
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

            configurationList = new OverviewViewDataBuilder().buildConfigurationPresentation(
                    getActivity(), presenter.getConfigurationList() );
            refreshAdapter();
        } else if( resultCode == Activity.RESULT_OK &&
                requestCode == UPDATE_CONFIGURATION_REQUEST_CODE ) {
            PokemonConfig pokemonConfig = data.getParcelableExtra(
                    ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY );
            presenter.onConfigurationUpdated(pokemonConfig);

            configurationList = new OverviewViewDataBuilder().buildConfigurationPresentation(
                    getActivity(), presenter.getConfigurationList() );
            refreshAdapter();
        }
    }

    private void showTypeQuickDetails( TypesPresentation type ) {
        TypesPresentation a = type;
    }

    public class ConfigurationSearchAdapter extends SearchAdapterDecorator<OverviewConfigurationRepresentation,
            ConfigurationSearchAdapter.ConfigViewHolder> {

        public ConfigurationSearchAdapter( ArrayList data ) {
            super(data);
        }

        public ConfigurationSearchAdapter(ArrayList data, SearchAdapterDecorator adapter) {
            super(data, adapter);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.pokemon_config_list_item_layout;
        }

        @Override
        public int getAdapterViewType() {
            return R.id.pokemon_configuration_view_id;
        }

        @Override
        public ConfigViewHolder createViewHolder( View rootView ) {
            final ConfigViewHolder viewHolder = new ConfigViewHolder(rootView);
            viewHolder.configName = rootView.findViewById(R.id.pokemon_config_name_text);

            viewHolder.pokemonName = rootView.findViewById(R.id.pokemon_name_text);
            viewHolder.pokemonType = rootView.findViewById(R.id.pokemon_type_text);
            viewHolder.pokemonType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if( position != NO_POSITION ) {
                        showTypeQuickDetails( data.get(position).type );
                    }
                }
            });
            viewHolder.baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

            viewHolder.item = rootView.findViewById(R.id.pokemon_item_text);
            viewHolder.ability = rootView.findViewById(R.id.pokemon_ability_text);
            viewHolder.nature = rootView.findViewById(R.id.pokemon_nature_text);
            return viewHolder;
        }

        @Override
        public void bindViewHolder(ConfigViewHolder holder,
                                     OverviewConfigurationRepresentation configuration) {
            holder.configName.setText(configuration.name);
            holder.pokemonName.setText(configuration.pokemonName);
            holder.pokemonType.setType(configuration.type);
            holder.baseStats.setText(configuration.totalStats);
            holder.item.setText(configuration.item);
            holder.ability.setText(configuration.ability);
            holder.nature.setText(configuration.nature);
        }

        public class ConfigViewHolder extends RecyclerView.ViewHolder {
            public TextView configName;

            public TextView item;
            public TextView ability;
            public TextView nature;
            public TextView baseStats;

            public TextView pokemonName;
            public TypeView pokemonType;

            public ConfigViewHolder(View rootView) {
                super(rootView);
            }
        }
    }
    
    public class PokemonSearchAdapter extends SearchAdapterDecorator<OverviewPokemonRepresentation,
            PokemonSearchAdapter.PokemonViewHolder> {

        public PokemonSearchAdapter(ArrayList<OverviewPokemonRepresentation> data) {
            super(data);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.pokemon_list_item_layout;
        }

        @Override
        public int getAdapterViewType() {
            return R.id.pokemon_view_id;
        }

        @Override
        public PokemonViewHolder createViewHolder( View rootView ) {
            PokemonViewHolder viewHolder = new PokemonViewHolder(rootView);

            viewHolder.pokemonName = rootView.findViewById(R.id.pokemon_name_text);
            viewHolder.pokemonType = rootView.findViewById(R.id.pokemon_type_text);
            viewHolder.baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

            viewHolder.pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
            viewHolder.pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
            viewHolder.pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
            viewHolder.pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
            viewHolder.pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
            viewHolder.pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);
            return viewHolder;
        }

        @Override
        public void bindViewHolder( PokemonViewHolder holder,
                                    OverviewPokemonRepresentation pokemon ) {
            holder.pokemonName.setText(pokemon.name);
            holder.pokemonType.setType(pokemon.type);
            holder.baseStats.setText(pokemon.totalStats);
            holder.pokemonHP.setText(pokemon.hp);
            holder.pokemonAttack.setText(pokemon.attack);
            holder.pokemonDefense.setText(pokemon.defense);
            holder.pokemonSpAttack.setText(pokemon.spAttack);
            holder.pokemonSpDefense.setText(pokemon.spDefense);
            holder.pokemonSpeed.setText(pokemon.speed);
        }


        public class PokemonViewHolder extends RecyclerView.ViewHolder {
            public OverviewPokemonRepresentation pokemon;

            public TextView pokemonName;
            public TypeView pokemonType;
            public TextView baseStats;

            public TextView pokemonHP;
            public TextView pokemonAttack;
            public TextView pokemonDefense;
            public TextView pokemonSpAttack;
            public TextView pokemonSpDefense;
            public TextView pokemonSpeed;

            public PokemonViewHolder(View rootView) {
                super(rootView);
            }
        }
    }
}
