package es.developer.achambi.pkmng.core.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base fragment, can override onBack event and allows options menu on action bar
 */
public abstract class BaseFragment extends Fragment {
    private int timesViewCreated = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "BaseFragment onCreate");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResource(), container, false);
        Log.i("TAG", "BaseFragment onCreateView");
        timesViewCreated++;
        return root;
    }

    public boolean isViewRecreated() {
        return timesViewCreated > 1;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getPresenter().onSaveInstanceState(outState);
    }

    public abstract int getLayoutResource();
    public abstract ViewPresenter getPresenter();

    public boolean onBackPressed() {
        return false;
    }
}
