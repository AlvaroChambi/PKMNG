package es.developer.achambi.pkmng.modules.search.move.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.move_value;
import es.developer.achambi.pkmng.modules.data.TypeDataAccess;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class MoveDataAccess {
    private static final String PHYSICAL = "physical";
    private static final String SPECIAL = "special";
    private static final String NON_DAMAGING = "non-damaging";

    private AppDatabase database;
    private TypeDataAccess typeDataAccess;

    public MoveDataAccess(AppDatabase database,
                          TypeDataAccess typeDataAccess) {
        this.database = database;
        this.typeDataAccess = typeDataAccess;
    }

    public ArrayList<Move> accessPokemonMovesData( int pokemonId ) {
        List<move_value> rawMoves = database.movesModel().getPokemonMoves( pokemonId );
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

    public Move accessMoveData(int moveId) {
        move_value rawMove = database.movesModel().getMove(moveId);
        Move move = new Move();
        move.setId( rawMove.id );
        move.setAccuracy(rawMove.accuracy);
        move.setPower(rawMove.power);
        move.setPp(rawMove.pp);
        move.setName(rawMove.name);
        move.setCategory(parseCategory(rawMove.category));
        move.setEffect(rawMove.shortEffect);
        move.setType(typeDataAccess.accessTypeData(rawMove.typeId));
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
