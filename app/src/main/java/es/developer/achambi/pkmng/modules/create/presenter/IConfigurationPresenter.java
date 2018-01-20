package es.developer.achambi.pkmng.modules.create.presenter;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;

public interface IConfigurationPresenter extends ViewPresenter {
    enum ConfigurationAction {
        CREATED,
        UPDATED,
        NONE
    };

    /**
     * @return configuration action that has been performed, CREATED if there wasn't a previous
     * configuration, UPDATED if the previous configuration has been updated, and NONE if the previous
     * configuration hasn't been changed
     */
    ConfigurationAction saveConfiguration(String name);
}
