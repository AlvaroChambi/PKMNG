package es.developer.achambi.pkmng.modules.overview.presenter;

import es.developer.achambi.pkmng.core.data.IOverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;

public class OverviewPresenterFactory implements IOverviewPresenterFactory {
    @Override
    public OverviewPresenter buildPresenter(IOverviewView view) {
        return new OverviewPresenter( view );
    }
}
