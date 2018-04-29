package es.developer.achambi.pkmng.modules.search.configuration.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public interface IConfigurationDataAccess {
    ArrayList<PokemonConfig> accessConfigurationData();
    int insertConfiguration(final PokemonConfig configuration);
    void updateConfiguration(final PokemonConfig configuration);
}
