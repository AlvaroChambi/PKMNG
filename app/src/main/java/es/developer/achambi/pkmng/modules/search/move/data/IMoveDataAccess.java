package es.developer.achambi.pkmng.modules.search.move.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public interface IMoveDataAccess {
    ArrayList<Move> accessPokemonMovesData(int pokemonId ) throws IllegalIDException;
    Move accessMoveData(int moveId) throws IllegalIDException;
}
