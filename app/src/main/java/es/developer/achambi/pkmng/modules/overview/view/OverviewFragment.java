package es.developer.achambi.pkmng.modules.overview.view;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.List;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.modules.details.view.ConfigurationDetailsFragment;
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.OverviewListAdapter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSuggestionsAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewListItemViewRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewViewDataBuilder;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;

public class OverviewFragment extends BaseRequestFragment implements IOverviewView{
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CONFIGURATION_DETAILS_DIALOG_TAG = "CONFIGURATION_DETAILS_DIALOG_TAG";

    private RecyclerView recyclerView;
    private OverviewListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<OverviewListItemViewRepresentation> viewRepresentation;

    private IOverviewPresenter presenter;

    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.overview_fragment_layout;
    }

    @Override
    public ViewPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewSetup(View view, Bundle savedInstanceState) {
        presenter = new OverviewPresenter(this);
        recyclerView = view.findViewById(R.id.overview_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        if(!isViewRecreated() && savedInstanceState == null) {
            doRequest();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewRepresentation = new OverviewViewDataBuilder()
                .buildViewRepresentation(getResources(),presenter.fetchPokemonList());
        adapter = new OverviewListAdapter( viewRepresentation );
        adapter.setOnItemClickedListener(presenter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void doRequest() {
        List<BasePokemon> pokemonList = presenter.fetchPokemonList();
        OverviewViewDataBuilder dataBuilder = new OverviewViewDataBuilder();

        viewRepresentation = dataBuilder.buildViewRepresentation(getResources(),pokemonList);
        adapter = new OverviewListAdapter( viewRepresentation );
        adapter.setOnItemClickedListener(presenter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showPokemonDetails(Pokemon pokemon) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PokemonDetailsFragment.newInstance(pokemon)
            .show(transaction, POKEMON_DETAILS_DIALOG_TAG );
    }

    @Override
    public void showConfigurationDetails(PokemonConfig configuration) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ConfigurationDetailsFragment.newInstance(configuration)
                .show(transaction, CONFIGURATION_DETAILS_DIALOG_TAG );
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
}
