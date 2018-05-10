package es.developer.achambi.pkmng.modules.search.move.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.MovesDAO;
import es.developer.achambi.pkmng.core.db.model.move_value;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class MoveDataAccess implements  IMoveDataAccess {
    private static final String PHYSICAL = "physical";
    private static final String SPECIAL = "special";
    private static final String NON_DAMAGING = "non-damaging";

    private MovesDAO movesDAO;
    private ITypeDataAccess typeDataAccess;

    public MoveDataAccess(MovesDAO movesDAO,
                          ITypeDataAccess typeDataAccess) {
        this.movesDAO = movesDAO;
        this.typeDataAccess = typeDataAccess;
    }

    /**
     * Access Moves data for the given pokemon id
     * @param pokemonId valid id's will never be lower than 1
     * @return List of moves that are valid for the given pokemon id
     * @throws IllegalIDException when an invalid id is requested
     */
    @Override
    public ArrayList<Move> accessPokemonMovesData( int pokemonId ) throws IllegalIDException {
        if( pokemonId < 1 ) {
            throw new IllegalIDException( pokemonId );
        }
        List<move_value> rawMoves = movesDAO.getPokemonMoves( pokemonId );
        ArrayList<Move> movesList = new ArrayList<>(rawMoves.size());
        for (move_value rawMove : rawMoves) {
            Move move = new Move();
            move.setId( rawMove.id );
            move.setAccuracy(rawMove.accuracy);
            move.setPower(rawMove.power);
            move.setPp(rawMove.pp);
            move.setName(rawMove.name);
            move.setCategory(parseCategory(rawMove.category));
            move.setEffect(rawMove.shortEffect);
            move.setType(typeDataAccess.accessTypeData(rawMove.typeId));
            movesList.add(move);
        }
        return movesList;
    }

    /**
     * Access Move data for the given id
     * @param moveId valid id's will never be lower than 1
     * @return Move that matches the given id or an empty Move instance if no match is found
     * @throws IllegalArgumentException when and invalid id is requested
     */
    @Override
    public Move accessMoveData(int moveId) throws IllegalIDException {
        if( moveId < 1 ) {
            throw new IllegalIDException( moveId );
        }
        move_value rawMove = movesDAO.getMove(moveId);
        Move move = new Move();
        if(rawMove != null) {
            move.setId( rawMove.id );
            move.setAccuracy(rawMove.accuracy);
            move.setPower(rawMove.power);
            move.setPp(rawMove.pp);
            move.setName(rawMove.name);
            move.setCategory(parseCategory(rawMove.category));
            move.setEffect(rawMove.shortEffect);
            move.setType(typeDataAccess.accessTypeData(rawMove.typeId));
        }
        return  move;
    }

    private Move.Category parseCategory( String value ) {
        Move.Category category = Move.Category.EMPTY;
        if( value.equals( PHYSICAL ) ) {
            return Move.Category.PHYSICAL;
        } else if( value.equals( SPECIAL ) ) {
            return Move.Category.SPECIAL;
        } else if( value.equals( NON_DAMAGING ) ) {
            return Move.Category.NON_DAMAGING;
        }
        return category;
    }
}
