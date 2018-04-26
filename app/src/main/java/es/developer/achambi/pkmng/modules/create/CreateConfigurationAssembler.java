package es.developer.achambi.pkmng.modules.create;

import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationPresenterFactory;

public class CreateConfigurationAssembler {
    private ConfigurationPresenterFactory presenterFactory;

    public ConfigurationPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ConfigurationPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
