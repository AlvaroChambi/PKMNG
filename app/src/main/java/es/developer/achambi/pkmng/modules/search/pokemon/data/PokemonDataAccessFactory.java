package es.developer.achambi.pkmng.modules.search.pokemon.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.IStatDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.ITypeDataAccessFactory;

public class PokemonDataAccessFactory implements IPokemonDataAccessFactory{
    private AppDatabase database;
    private IStatDataAccessFactory statDataAccessFactory;
    private ITypeDataAccessFactory typeDataAccessFactory;

    public PokemonDataAccessFactory(AppDatabase database,
                                    IStatDataAccessFactory statDataAccessFactory,
                                    ITypeDataAccessFactory typeDataAccessFactory) {
        this.database = database;
        this.statDataAccessFactory = statDataAccessFactory;
        this.typeDataAccessFactory = typeDataAccessFactory;
    }

    @Override
    public PokemonDataAccess buildDataAccess() {
        return new PokemonDataAccess( database,
                statDataAccessFactory.buildDataAccess(),
                typeDataAccessFactory.buildDataAccess() );
    }
}
