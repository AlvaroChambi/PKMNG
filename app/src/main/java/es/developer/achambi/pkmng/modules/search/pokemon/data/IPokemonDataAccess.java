package es.developer.achambi.pkmng.modules.search.pokemon.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public interface IPokemonDataAccess {
    /**
     * Access the list of pokemon
     * @return list of pokemon
     */
    ArrayList<Pokemon> accessData();

    /**
     * Access the pokemon data that matches the given id
     * @param pokemonId of the requested pokemon, id's should be higher than 0
     * @return Pokemon data if id is found, empty Pokemon otherwise
     * @throws IllegalIDException when an invalid id is requested
     */
    Pokemon accessPokemonData(int pokemonId) throws IllegalIDException;

    ArrayList<Pokemon> queryData( String query );

    /**
     * Clears local memory cache
     */
    void clearCache();
}
