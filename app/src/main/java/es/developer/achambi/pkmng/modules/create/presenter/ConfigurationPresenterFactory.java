package es.developer.achambi.pkmng.modules.create.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;

public class ConfigurationPresenterFactory {
    private IConfigurationDataAccess dataAccess;
    private MainExecutor executor;

    public ConfigurationPresenterFactory(IConfigurationDataAccess dataAccess,
                                         MainExecutor executor) {
        this.dataAccess = dataAccess;
        this.executor = executor;
    }

    public ConfigurationPresenter buildPresenter(Screen screen) {
        return new ConfigurationPresenter( screen, dataAccess, executor );
    }
}
