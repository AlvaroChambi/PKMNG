package es.developer.achambi.pkmng.core;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.BaseAppWiring;
import es.developer.achambi.pkmng.modules.search.configuration.data.ConfigurationDataAccess;

public class AppWiring extends BaseAppWiring {
    public static MainExecutor executor;

    @Override
    public MainExecutor buildExecutor() {
        executor = super.buildExecutor();
        return executor;
    }
}
