package es.developer.achambi.pkmng.modules.create.presenter;

import android.os.Bundle;

import es.developer.achambi.pkmng.modules.create.view.ICreateConfigurationView;
import es.developer.achambi.pkmng.modules.create.view.StatEVView;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class CreateConfigurationPresenter implements ICreateConfigurationPresenter,
        StatEVView.ProgressUpdateProvider {
    private static final String EV_SAVED_DATA_TAG = "EV_SAVED_DATA_TAG";
    private ICreateConfigurationView view;
    private StatsSet evData;

    public CreateConfigurationPresenter( ICreateConfigurationView view ) {
        this.view = view;
        evData = new StatsSet();
    }

    @Override
    public int requestValueIncrement(Stat stat, int progress) {
        int totalStatsPreview = evData.getTotalStatsPreview( stat, progress );
        if( totalStatsPreview <= StatsSet.MAX_TOTAL_EVS ) {
            evData.getStats().put(stat, progress);
            return progress;
        } else if( totalStatsPreview > StatsSet.MAX_TOTAL_EVS ){
            return progress + ( StatsSet.MAX_TOTAL_EVS - totalStatsPreview );
        }
        return progress;
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
