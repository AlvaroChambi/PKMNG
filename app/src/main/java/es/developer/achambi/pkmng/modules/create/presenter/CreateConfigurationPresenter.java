package es.developer.achambi.pkmng.modules.create.presenter;

import android.os.Bundle;

import es.developer.achambi.pkmng.modules.create.view.ICreateConfigurationView;
import es.developer.achambi.pkmng.modules.create.view.StatEVView;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class CreateConfigurationPresenter implements ICreateConfigurationPresenter,
        StatEVView.OnValueChangedListener {
    private static final String EV_SAVED_DATA_TAG = "EV_SAVED_DATA_TAG";
    private ICreateConfigurationView view;
    private StatsSet evData;

    public CreateConfigurationPresenter( ICreateConfigurationView view ) {
        this.view = view;
        evData = new StatsSet();
    }

    @Override
    public void onValueChanged(Stat stat, int value) {
        if(evData.getStats().get(stat) != value) {
            evData.getStats().put(stat, value);
            view.onEVValueUpdated( evData );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable( EV_SAVED_DATA_TAG, evData );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        evData = bundle.getParcelable( EV_SAVED_DATA_TAG );
    }

    @Override
    public StatsSet getEvSet() {
        return evData;
    }
}
