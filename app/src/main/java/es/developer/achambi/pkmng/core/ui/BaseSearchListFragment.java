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
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public int getLayoutResource() {
        return R.layout.base_search_fragment_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        inflateHeaderView( inflater, ((ViewGroup)root.findViewById(R.id.base_search_header_frame)) );
        return root;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.base_search_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager( layoutManager );
    }

    public void refreshAdapter(){
        recyclerView.setAdapter( new BaseSearchAdapter( provideAdapter() ) );
    }

    public abstract SearchAdapterDecorator provideAdapter();

    /**/
    public boolean inflateHeaderView( LayoutInflater inflater, ViewGroup root ) {
        return false;
    }
}
