package es.developer.achambi.pkmng.modules.create.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccessFactory;

public class ConfigurationPresenterFactory {
    private IConfigurationDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public ConfigurationPresenterFactory(IConfigurationDataAccessFactory dataAccessFactory,
                                         MainExecutor executor) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    public ConfigurationPresenter buildPresenter(Screen screen) {
        return new ConfigurationPresenter( screen, dataAccessFactory.buildDataAccess(), executor );
    }
}
