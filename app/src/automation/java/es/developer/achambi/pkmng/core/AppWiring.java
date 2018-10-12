package es.developer.achambi.pkmng.core;

import android.content.Context;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.BaseAppWiring;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.data.MockConfigurationDataAccess;

public class AppWiring extends BaseAppWiring {
    public static MainExecutor executor;

    @Override
    public void appWiring(Context context) {
        super.appWiring(context);
        ConfigurationDataAssembler mockAssembler = new ConfigurationDataAssembler(){
            @Override
            public IConfigurationDataAccess getConfigurationDataAccess() {
                return new MockConfigurationDataAccess();
            }
        };
        AppWiring.createConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
        AppWiring.searchConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
    }

    @Override
    public MainExecutor buildExecutor() {
        executor = super.buildExecutor();
        return executor;
    }
}
