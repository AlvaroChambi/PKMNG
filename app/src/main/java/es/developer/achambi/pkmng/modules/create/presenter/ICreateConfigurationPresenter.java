package es.developer.achambi.pkmng.modules.create.presenter;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public interface ICreateConfigurationPresenter extends ViewPresenter {
    StatsSet getEvSet();
    PokemonConfig createConfiguration( String name );
    boolean saveConfiguration();
}
