package es.developer.achambi.coreframework.ui;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.developer.achambi.coreframework.R;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String BASE_ARGUMENTS = "base_arguments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        setTitle(getScreenTitle());
        if(savedInstanceState == null) {
            attachFragment();
        }
    }

    public abstract int getScreenTitle();

    public void attachFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle args = getIntent().getBundleExtra(BASE_ARGUMENTS);

        transaction.add(R.id.fragment_frame, getFragment(args));
        transaction.commit();
    }

    public abstract BaseFragment getFragment( Bundle args );

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
