package es.developer.achambi.pkmng.modules.overview.presenter;

import android.util.Log;

import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;

public class OverviewPresenter implements IOverviewPresenter{
    private static final String TAG = OverviewPresenter.class.getCanonicalName();
    private IOverviewView view;

    public OverviewPresenter(IOverviewView view) {
        this.view = view;
    }

    @Override
    public void onQueryTextSubmit(String query) {
        Log.i(TAG, "query text submitted: " + query);
    }

    @Override
    public void onQueryTextChanged(String query) {
        Log.i(TAG, "query text changed: " + query);
    }
}
