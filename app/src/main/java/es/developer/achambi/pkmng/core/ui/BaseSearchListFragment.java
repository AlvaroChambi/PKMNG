package es.developer.achambi.pkmng.core.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import es.developer.achambi.pkmng.R;

public abstract class BaseSearchListFragment extends BaseRequestFragment {
    private static int NO_ID = -1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ViewGroup header;
    private BaseSearchAdapter adapter;
    protected RequestManager requestManager;

    @Override
    public int getLayoutResource() {
        return R.layout.base_search_fragment_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = Glide.with(this);
        adapter = new BaseSearchAdapter( provideAdapter() );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = ((ViewGroup) super.onCreateView(inflater, container, savedInstanceState));
        if( getHeaderLayoutResource() != NO_ID ) {
            header = root.findViewById(R.id.base_search_header_frame);
            inflater.inflate( getHeaderLayoutResource(), header );
        }
        return root;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if( getHeaderLayoutResource() != NO_ID ) {
            onHeaderSetup( header );
        }
        recyclerView = view.findViewById(R.id.base_search_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( adapter );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.overview_search_action)
                .getActionView();
        searchView.setQueryHint(getResources().getString(getSearchHintResource()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextSubmitted( query );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItem item = menu.findItem(R.id.overview_search_action);
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                onSearchFinished();
                return true;
            }
        });
    }

    @Override
    public int getLoadingFrame() {
        return R.id.base_request_loading_frame;
    }

    public void onQueryTextSubmitted(String query) {

    }

    public void onSearchFinished(){

    }

    public void presentAdapterData( ) {
        adapter.updateData();
    }

    public int getSearchHintResource() {
        return R.string.search_default_hint;
    }

    public abstract SearchAdapterDecorator provideAdapter();

    public int getHeaderLayoutResource() {
        return NO_ID;
    }

    public void onHeaderSetup( View header ) {
        return;
    }
}
