package es.developer.achambi.pkmng.core.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import es.developer.achambi.pkmng.R;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String BASE_ARGUMENTS = "base_arguments";
    private BaseFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        attachFragment();
    }

    public void attachFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle args = getIntent().getBundleExtra(BASE_ARGUMENTS);
        fragment = getFragment();
        fragment.setArguments(args);

        transaction.add(R.id.fragment_frame, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    public abstract BaseFragment getFragment();

    @Override
    public void onBackPressed() {
        if(!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
