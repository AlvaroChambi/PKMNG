package es.developer.achambi.pkmng.modules.search.configuration.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.configuration.screen.ISearchConfigurationScreen;

public class SearchConfigurationPresenterFactory implements ISearchConfigurationPresenterFactory{
    private IConfigurationDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public SearchConfigurationPresenterFactory(IConfigurationDataAccessFactory dataAccessFactory,
                                               MainExecutor executor) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    @Override
    public SearchConfigurationPresenter buildPresenter(ISearchConfigurationScreen screen) {
        return new SearchConfigurationPresenter(screen, dataAccessFactory.buildDataAccess(),
                executor);
    }
}
