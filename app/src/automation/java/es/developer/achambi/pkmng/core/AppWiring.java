package es.developer.achambi.pkmng.core;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.BaseAppWiring;
import es.developer.achambi.pkmng.modules.search.configuration.data.ConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccessFactory;

public class AppWiring extends BaseAppWiring {
    public static MainExecutor executor;

    @Override
    protected IConfigurationDataAccessFactory overrideConfigurationDataAccess(
            IConfigurationDataAccessFactory dataAccessFactory) {
        return new IConfigurationDataAccessFactory() {
            @Override
            public ConfigurationDataAccess buildDataAccess() {
                return new MockConfigurationDataAccess();
            }
        };
    }

    @Override
    public MainExecutor buildExecutor() {
        executor = super.buildExecutor();
        return executor;
    }
}
