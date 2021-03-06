package es.developer.achambi.pkmng.modules.search.pokemon.data;


import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.database.dao.PokemonDAO;
import es.developer.achambi.pkmng.database.model.pokemon;
import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.coreframework.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public class PokemonDataAccess implements IPokemonDataAccess{
    private PokemonDAO pokemonDAO;
    private IStatDataAccess statDataAccess;
    private ITypeDataAccess typeDataAccess;
    private ImageResourceBuilder imageResourceBuilder;

    private ArrayList<Pokemon> cachedData;

    public PokemonDataAccess( PokemonDAO pokemonDAO,
                              IStatDataAccess statDataAccess,
                              ITypeDataAccess typeDataAccess,
                              ImageResourceBuilder imageResourceBuilder ) {
        this.pokemonDAO = pokemonDAO;
        this.statDataAccess = statDataAccess;
        this.typeDataAccess = typeDataAccess;
        this.imageResourceBuilder = imageResourceBuilder;
    }

    @Override
    public ArrayList<Pokemon> accessData() {
        if(cachedData != null) {
            return cachedData;
        }
        List<pokemon> pokemonArray = pokemonDAO.getPokemon();
        ArrayList<Pokemon> pokemonList = new ArrayList<>( pokemonArray.size() );
        for( pokemon currentPokemon : pokemonArray ) {
            Pokemon pokemon = new Pokemon(currentPokemon.id);
            pokemon.setName(currentPokemon.identifier);
            pokemon.setBaseImageUrl( imageResourceBuilder.buildPokemonImageIdentifier(
                    currentPokemon.species_id, currentPokemon.identifier ) );
            pokemon.setType( typeDataAccess.accessPokemonTypeData( currentPokemon.id ) );
            pokemon.setStats( statDataAccess.accessPokemonStatsData( currentPokemon.id ) );

            pokemonList.add( pokemon );
        }
        cachedData = pokemonList;
        return pokemonList;
    }

    @Override
    public Pokemon accessPokemonData(int pokemonId) throws IllegalIDException {
        if( pokemonId < 1 ) {
            throw new IllegalIDException( pokemonId );
        }
        pokemon rawPokemon = pokemonDAO.getPokemon(pokemonId);
        if( rawPokemon != null ) {
            Pokemon pokemon = new Pokemon(rawPokemon.id);
            pokemon.setName(rawPokemon.identifier);
            pokemon.setBaseImageUrl( imageResourceBuilder.buildPokemonImageIdentifier(
                    rawPokemon.species_id, rawPokemon.identifier ) );
            pokemon.setType( typeDataAccess.accessPokemonTypeData( rawPokemon.id ) );
            pokemon.setStats( statDataAccess.accessPokemonStatsData( rawPokemon.id ) );
            return pokemon;
        } else {
            return new Pokemon();
        }
    }

    @Override
    public ArrayList<Pokemon> queryData(String query) {
        if(query == null) {
            return new ArrayList<>();
        }
        List<pokemon> pokemonArray = pokemonDAO.getPokemon( "%" + query + "%" );
        ArrayList<Pokemon> pokemonList = new ArrayList<>( pokemonArray.size() );

        for( pokemon currentPokemon : pokemonArray ) {
            Pokemon pokemon = new Pokemon(currentPokemon.id);
            pokemon.setName(currentPokemon.identifier);
            pokemon.setBaseImageUrl( imageResourceBuilder.buildPokemonImageIdentifier(
                    currentPokemon.species_id, currentPokemon.identifier ) );
            pokemon.setType( typeDataAccess.accessPokemonTypeData( currentPokemon.id ) );
            pokemon.setStats( statDataAccess.accessPokemonStatsData( currentPokemon.id ) );

            pokemonList.add( pokemon );
        }
        return pokemonList;
    }
}
