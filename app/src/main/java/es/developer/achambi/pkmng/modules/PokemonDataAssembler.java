package es.developer.achambi.pkmng.modules;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.stat.StatDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;

public class PokemonDataAssembler {
    private AppDatabase appDatabase;
    private TypeDataAccessFactory typeDataAccessFactory;
    private StatDataAccessFactory statDataAccessFactory;
    private PokemonDataAccessFactory pokemonDataAccessFactory;

    public PokemonDataAssembler setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        return this;
    }

    public PokemonDataAssembler setTypeDataAccessFactory(
            TypeDataAccessFactory typeDataAccessFactory) {
        this.typeDataAccessFactory = typeDataAccessFactory;
        return this;
    }

    public PokemonDataAssembler setStatDataAccessFactory(
            StatDataAccessFactory statDataAccessFactory) {
        this.statDataAccessFactory = statDataAccessFactory;
        return this;
    }

    public PokemonDataAssembler setPokemonDataAccessFactory(
            PokemonDataAccessFactory pokemonDataAccessFactory) {
        this.pokemonDataAccessFactory = pokemonDataAccessFactory;
        return this;
    }

    public PokemonDataAccess getPokemonDataAccess() {
        return pokemonDataAccessFactory.buildDataAccess(appDatabase.pokemonModel(),
                statDataAccessFactory.buildDataAccess( appDatabase.statsModel() ),
                typeDataAccessFactory.buildDataAccess( appDatabase.typeModel() ));
    }
}
