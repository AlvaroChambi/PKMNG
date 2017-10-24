package es.developer.achambi.pkmng.modules.overview.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.OverviewListAdapter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSuggestionsAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewListItemViewRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewViewDataBuilder;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;

public class OverviewFragment extends BaseRequestFragment implements IOverviewView{
    private static final String VIEW_SAVED_STATE = "VIEW_SAVED_STATE";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new OverviewPresenter(this);
        recyclerView = view.findViewById(R.id.overview_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        if(savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else if(!isViewRecreated()) {
            doRequest();
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        viewRepresentation = savedInstanceState.getParcelableArrayList(VIEW_SAVED_STATE);
        adapter = new OverviewListAdapter( viewRepresentation );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void doRequest() {
        List<BasePokemon> pokemonList = presenter.fetchPokemonList();
        OverviewViewDataBuilder dataBuilder = new OverviewViewDataBuilder();

        viewRepresentation = dataBuilder.buildViewRepresentation(getResources(),pokemonList);
        adapter = new OverviewListAdapter( viewRepresentation );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(VIEW_SAVED_STATE, (ArrayList)viewRepresentation);
    }
}
