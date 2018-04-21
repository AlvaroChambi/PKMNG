package es.developer.achambi.pkmng.modules.search.pokemon.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.IStatDataAccessFactory;

public class PokemonDataAccessFactory implements IPokemonDataAccessFactory{
    private  AppDatabase database;
    private IStatDataAccessFactory statDataAccessFactory;

    public PokemonDataAccessFactory(AppDatabase database, IStatDataAccessFactory statDataAccessFactory) {
        this.database = database;
        this.statDataAccessFactory = statDataAccessFactory;
    }

    @Override
    public PokemonDataAccess buildDataAccess() {
        return new PokemonDataAccess( database, statDataAccessFactory.buildDataAccess() );
    }
}
