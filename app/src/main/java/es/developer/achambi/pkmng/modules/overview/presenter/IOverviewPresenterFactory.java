package es.developer.achambi.pkmng.modules.overview.presenter;

import es.developer.achambi.pkmng.modules.overview.view.IOverviewScreen;

public interface IOverviewPresenterFactory {
    OverviewPresenter buildPresenter( IOverviewScreen screen );
}
