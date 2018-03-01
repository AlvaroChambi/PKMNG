package es.developer.achambi.pkmng.core.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.developer.achambi.pkmng.R;

public abstract class BaseSearchListFragment extends BaseRequestFragment {
    private static int NO_ID = -1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ViewGroup header;

    @Override
    public int getLayoutResource() {
        return R.layout.base_search_fragment_layout;
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
        onHeaderSetup( header );
        recyclerView = view.findViewById(R.id.base_search_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager( layoutManager );
    }

    public void refreshAdapter(){
        recyclerView.setAdapter( new BaseSearchAdapter( provideAdapter() ) );
    }

    public abstract SearchAdapterDecorator provideAdapter();

    public int getHeaderLayoutResource() {
        return NO_ID;
    }

    public void onHeaderSetup( View header ) {
        return;
    }
}
