package es.developer.achambi.pkmng.modules;

import es.developer.achambi.coreframework.db.dao.PokemonDAO;
import es.developer.achambi.coreframework.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.search.StatDataAssembler;
import es.developer.achambi.pkmng.modules.search.TypeDataAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;

public class PokemonDataAssembler {
    private PokemonDAO pokemonDAO;
    private TypeDataAssembler typeDataAssembler;
    private StatDataAssembler statDataAssembler;
    private ImageResourceBuilder imageResourceBuilder;
    private PokemonDataAccessFactory pokemonDataAccessFactory;

    public PokemonDataAssembler setPokemonDAO(PokemonDAO pokemonDAO) {
        this.pokemonDAO = pokemonDAO;
        return this;
    }

    public PokemonDataAssembler setTypeDataAssembler(TypeDataAssembler typeDataAssembler) {
        this.typeDataAssembler = typeDataAssembler;
        return this;
    }

    public PokemonDataAssembler setStatDataAssembler(StatDataAssembler statDataAssembler) {
        this.statDataAssembler = statDataAssembler;
        return this;
    }

    public PokemonDataAssembler setPokemonDataAccessFactory(
            PokemonDataAccessFactory pokemonDataAccessFactory) {
        this.pokemonDataAccessFactory = pokemonDataAccessFactory;
        return this;
    }

    public PokemonDataAssembler setImageResourceBuilder(ImageResourceBuilder imageResourceBuilder) {
        this.imageResourceBuilder = imageResourceBuilder;
        return this;
    }

    public IPokemonDataAccess getPokemonDataAccess() {
        return pokemonDataAccessFactory.buildDataAccess(pokemonDAO,
                statDataAssembler.getStatDataAccess(),
                typeDataAssembler.getDataAccess(), imageResourceBuilder );
    }
}
