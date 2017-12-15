package es.developer.achambi.pkmng.core.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.developer.achambi.pkmng.R;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String BASE_ARGUMENTS = "base_arguments";
    private BaseFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        if(savedInstanceState == null) {
            setTitle(getScreenTitle());
            attachFragment();
        }
    }

    public abstract int getScreenTitle();

    public void attachFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle args = getIntent().getBundleExtra(BASE_ARGUMENTS);
        fragment = getFragment(args);

        transaction.add(R.id.fragment_frame, fragment);
        transaction.commit();
    }

    public abstract BaseFragment getFragment( Bundle args );

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if(!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
