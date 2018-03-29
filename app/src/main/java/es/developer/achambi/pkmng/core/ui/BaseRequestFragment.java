package es.developer.achambi.pkmng.core.ui;

import android.view.View;

public abstract class BaseRequestFragment extends BaseFragment {
    public void doRequest() {
        View progressBar = getView().findViewById(getProgressBarId());
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideLoading() {
        View progressBar = getView().findViewById(getProgressBarId());
        progressBar.setVisibility(View.GONE);
    }

    public abstract int getProgressBarId();
}
