package es.developer.achambi.pkmng.modules.overview;

import es.developer.achambi.pkmng.modules.overview.presenter.IOverviewPresenterFactory;

public class OverviewAssembler {
    private IOverviewPresenterFactory presenterFactory;

    public IOverviewPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(IOverviewPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
