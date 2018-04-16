package es.developer.achambi.pkmng.modules.search.pokemon.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;

public class PokemonDataAccessFactory implements IPokemonDataAccessFactory{
    private  AppDatabase database;
    public PokemonDataAccessFactory( AppDatabase database ) {
        this.database = database;
    }

    @Override
    public PokemonDataAccess buildDataAccess() {
        return new PokemonDataAccess( database );
    }
}
