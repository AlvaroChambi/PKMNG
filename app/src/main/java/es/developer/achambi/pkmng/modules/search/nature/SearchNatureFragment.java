package es.developer.achambi.pkmng.modules.search.nature;

import android.os.Bundle;

import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;

public class SearchNatureFragment extends BaseSearchListFragment {

    public static final SearchNatureFragment newInstance( Bundle args ) {
        SearchNatureFragment fragment = new SearchNatureFragment();
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void doRequest() {

    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        return null;
    }
}
