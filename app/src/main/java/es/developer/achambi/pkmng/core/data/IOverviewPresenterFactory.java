package es.developer.achambi.pkmng.core.data;

import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;

public interface IOverviewPresenterFactory {
    OverviewPresenter buildPresenter( IOverviewView view );
}
