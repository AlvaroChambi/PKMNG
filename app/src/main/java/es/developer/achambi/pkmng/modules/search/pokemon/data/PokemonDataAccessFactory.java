package es.developer.achambi.pkmng.modules.search.pokemon.data;

import es.developer.achambi.pkmng.core.data.IPokemonDataAccessFactory;
import es.developer.achambi.pkmng.core.db.AppDatabase;

public class PokemonDataAccessFactory implements IPokemonDataAccessFactory{
    @Override
    public PokemonDataAccess buildDataAccess( AppDatabase database ) {
        return new PokemonDataAccess( database );
    }
}
