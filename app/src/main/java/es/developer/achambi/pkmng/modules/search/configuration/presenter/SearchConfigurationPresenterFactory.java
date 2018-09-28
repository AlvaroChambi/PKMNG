package es.developer.achambi.pkmng.modules.search.configuration.presenter;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.screen.ISearchConfigurationScreen;

public class SearchConfigurationPresenterFactory implements ISearchConfigurationPresenterFactory{
    private IConfigurationDataAccess dataAccess;
    private MainExecutor executor;

    public SearchConfigurationPresenterFactory(IConfigurationDataAccess dataAccess,
                                               MainExecutor executor) {
        this.dataAccess = dataAccess;
        this.executor = executor;
    }

    @Override
    public SearchConfigurationPresenter buildPresenter(ISearchConfigurationScreen screen) {
        return new SearchConfigurationPresenter(screen, dataAccess, executor);
    }
}
