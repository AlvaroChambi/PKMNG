package es.developer.achambi.pkmng.modules.overview.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.OverviewListAdapter;
import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;

public class OverviewFragment extends BaseFragment implements IOverviewView{
    private static final String TAG = OverviewFragment.class.getCanonicalName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new OverviewPresenter(this);

        recyclerView = view.findViewById(R.id.overview_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new OverviewListAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.overview_search_action).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_pokemon_hint));

        //TODO Maybe some basic custom FilterSearchView: can translate this events to something more precise
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onQueryTextSubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onQueryTextChanged(newText);
                return true;
            }
        });
    }
}
