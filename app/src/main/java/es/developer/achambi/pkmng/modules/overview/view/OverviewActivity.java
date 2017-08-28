package es.developer.achambi.pkmng.modules.overview.view;

import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;

public class OverviewActivity extends BaseActivity{
    @Override
    public BaseFragment getFragment() {
        return OverviewFragment.newInstance();
    }

    @Override
    public int getScreenTitle() {
        return R.string.overview_activity_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }
}
