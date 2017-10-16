package es.developer.achambi.pkmng.core.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResource(), container, false);
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

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getPresenter().onRestoreInstanceState(savedInstanceState);
    }

    public abstract int getLayoutResource();
    public abstract ViewPresenter getPresenter();

    public boolean onBackPressed() {
        return false;
    }
}
