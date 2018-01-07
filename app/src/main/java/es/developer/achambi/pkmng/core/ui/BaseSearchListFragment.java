package es.developer.achambi.pkmng.core.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import es.developer.achambi.pkmng.R;

public abstract class BaseSearchListFragment extends BaseRequestFragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public int getLayoutResource() {
        return R.layout.base_search_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.base_search_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager( layoutManager );
    }

    public void refreshAdapter(){
        if( !recyclerView.isComputingLayout() ) {
            recyclerView.setAdapter( new BaseSearchAdapter( provideAdapter() ) );
        }
    }

    public abstract SearchAdapterDecorator provideAdapter();
}
