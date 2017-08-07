package es.developer.achambi.pkmng.modules;

import android.os.Bundle;

import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;

public class OverviewActivity extends BaseActivity{
    @Override
    public BaseFragment getFragment() {
        return OverviewFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }
}
