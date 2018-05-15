package es.developer.achambi.pkmng.modules.search.move.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public interface IMoveDataAccess {
    /**
     * Access Moves data for the given pokemon id
     * @param pokemonId valid id's will never be lower than 1
     * @return List of moves that are valid for the given pokemon id
     * @throws IllegalIDException when an invalid id is requested
     */
    ArrayList<Move> accessPokemonMovesData(int pokemonId ) throws IllegalIDException;
    /**
     * Access Move data for the given id
     * @param moveId valid id's will never be lower than 1
     * @return Move that matches the given id or an empty Move instance if no match is found
     * @throws IllegalArgumentException when and invalid id is requested
     */
    Move accessMoveData(int moveId) throws IllegalIDException;
}
