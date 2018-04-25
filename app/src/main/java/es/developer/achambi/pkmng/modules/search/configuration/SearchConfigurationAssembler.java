package es.developer.achambi.pkmng.modules.search.configuration;

import es.developer.achambi.pkmng.modules.search.configuration.presenter.ISearchConfigurationPresenterFactory;

public class SearchConfigurationAssembler {
    private ISearchConfigurationPresenterFactory presenterFactory;

    public ISearchConfigurationPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ISearchConfigurationPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
