package es.developer.achambi.pkmng.modules.search.configuration.presenter;

import es.developer.achambi.pkmng.modules.search.configuration.screen.ISearchConfigurationScreen;

public interface ISearchConfigurationPresenterFactory {
    SearchConfigurationPresenter buildPresenter(ISearchConfigurationScreen screen);
}
