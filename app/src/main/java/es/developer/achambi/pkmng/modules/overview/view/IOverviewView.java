package es.developer.achambi.pkmng.modules.overview.view;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public interface IOverviewView {
    void showPokemonDetails( Pokemon pokemon );
    void showConfigurationDetails( PokemonConfig configuration );
}
