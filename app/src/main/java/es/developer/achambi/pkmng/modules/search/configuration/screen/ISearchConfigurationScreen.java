package es.developer.achambi.pkmng.modules.search.configuration.screen;

import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public interface ISearchConfigurationScreen extends Screen {
    void showConfigurationDetails( PokemonConfig configuration );
}
