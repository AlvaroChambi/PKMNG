package es.developer.achambi.coreframework.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public abstract class BaseDialogFragment extends DialogFragment {
    private int timesViewCreated = 0;

    //Forces the dialog decorView to match parent in all cases
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
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
        onViewSetup(view, savedInstanceState);
        if(savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getPresenter().onSaveInstanceState(outState);
    }

    /**
     * Called whenever the fragment state is being recreated, bundle will always be available.
     * This event always happens after onViewCreated() is called.
     * Call super to allow presenter to restore it's state
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        getPresenter().onRestoreInstanceState(savedInstanceState);
    }

    public boolean isViewRecreated() {
        return timesViewCreated > 1;
    }

    public Presenter getPresenter() {
        return new NullPresenter();
    }

    public abstract int getLayoutResource();
    public abstract void onViewSetup(View view, @Nullable Bundle savedInstanceState);
}
