package es.developer.achambi.pkmng.core.ui;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base fragment, can override onBack event and allows options menu on action bar
 */
public abstract class BaseFragment extends Fragment implements Screen {
    private int timesViewCreated = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setupPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResource(), container, false);
        timesViewCreated++;
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        onViewSetup(view, savedInstanceState);
    }

    public boolean isViewRecreated() {
        return timesViewCreated > 1;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setupPresenter().onSaveInstanceState(outState);
    }

    @Override
    public Lifecycle screenLifecycle() {
         return getLifecycle();
    }

    /**
     * Called whenever the fragment state is being recreated, bundle will always be available.
     * This event always happens after onViewCreated() is called.
     * Call super to allow presenter to restore it's state
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        setupPresenter().onRestoreInstanceState(savedInstanceState);
    }

    public Presenter setupPresenter() {
        return new NullPresenter();
    }

    public abstract int getLayoutResource();
    public abstract void onViewSetup(View view, @Nullable Bundle savedInstanceState);
}
