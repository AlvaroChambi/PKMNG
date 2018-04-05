package es.developer.achambi.pkmng.modules.search.configuration.view;

import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public interface ISearchConfigurationScreen extends Screen {
    void showConfigurationDetails( PokemonConfig configuration );
}
