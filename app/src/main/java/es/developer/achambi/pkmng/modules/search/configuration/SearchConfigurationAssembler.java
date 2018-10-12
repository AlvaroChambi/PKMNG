package es.developer.achambi.pkmng.modules.search.configuration;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.ISearchConfigurationPresenterFactory;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenterFactory;

public class SearchConfigurationAssembler {
    private MainExecutor mainExecutor;
    private ConfigurationDataAssembler configurationDataAssembler;

    public SearchConfigurationAssembler setMainExecutor(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
        return this;
    }

    public SearchConfigurationAssembler setConfigurationDataAssembler(
            ConfigurationDataAssembler configurationDataAssembler) {
        this.configurationDataAssembler = configurationDataAssembler;
        return this;
    }

    public ISearchConfigurationPresenterFactory getPresenterFactory() {
        return new SearchConfigurationPresenterFactory(
                configurationDataAssembler.getConfigurationDataAccess(),
                mainExecutor );
    }
}
