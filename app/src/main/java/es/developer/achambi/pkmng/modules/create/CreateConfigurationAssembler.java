package es.developer.achambi.pkmng.modules.create;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationPresenterFactory;

public class CreateConfigurationAssembler {
    private MainExecutor executor;
    private ConfigurationDataAssembler configurationDataAssembler;

    public CreateConfigurationAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public CreateConfigurationAssembler setConfigurationDataAssembler(
            ConfigurationDataAssembler configurationDataAssembler) {
        this.configurationDataAssembler = configurationDataAssembler;
        return this;
    }

    public ConfigurationPresenterFactory getPresenterFactory() {
        return new ConfigurationPresenterFactory(
                configurationDataAssembler.getConfigurationDataAccess(), executor );
    }
}
