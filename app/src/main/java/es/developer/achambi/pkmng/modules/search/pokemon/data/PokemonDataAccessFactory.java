package es.developer.achambi.pkmng.modules.search.pokemon.data;

import es.developer.achambi.pkmng.core.db.dao.PokemonDAO;
import es.developer.achambi.pkmng.core.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;

public class PokemonDataAccessFactory {
    public PokemonDataAccess buildDataAccess(PokemonDAO pokemonDAO,
                                             IStatDataAccess statDataAccess,
                                             ITypeDataAccess typeDataAccess,
                                             ImageResourceBuilder imageResourceBuilder ) {
        return new PokemonDataAccess( pokemonDAO, statDataAccess, typeDataAccess,
                imageResourceBuilder );
    }
}
