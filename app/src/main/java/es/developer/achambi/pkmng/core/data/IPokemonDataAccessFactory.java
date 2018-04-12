package es.developer.achambi.pkmng.core.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess;

public interface IPokemonDataAccessFactory {
    PokemonDataAccess buildDataAccess( AppDatabase appDatabase );
}
