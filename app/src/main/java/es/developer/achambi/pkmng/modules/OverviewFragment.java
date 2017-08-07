package es.developer.achambi.pkmng.modules;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseFragment;

public class OverviewFragment extends BaseFragment {

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }
}
