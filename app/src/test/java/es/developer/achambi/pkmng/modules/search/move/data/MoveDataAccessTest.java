package es.developer.achambi.pkmng.modules.search.move.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.MovesDAO;
import es.developer.achambi.pkmng.core.db.model.move_value;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveDataAccessTest {
    private MoveDataAccess moveDataAccess;
    private MovesDAO movesDAO;
    private TypeDataAccess typeDataAccess;

    @Before
    public void setup() {
        movesDAO = mock(MovesDAO.class);
        typeDataAccess = mock(TypeDataAccess.class);
        moveDataAccess = new MoveDataAccess(movesDAO, typeDataAccess);
    }

    @Test
    public void accessMoveDataFound() {
        when(typeDataAccess.accessTypeData(1)).thenReturn(Type.ICE);
        when(movesDAO.getMove(1)).thenReturn(buildMove());

        Move move = moveDataAccess.accessMoveData( 1 );
        assertMove( move );
    }

    @Test
    public void accessMoveDataNotFound() {
        when(movesDAO.getMove(1)).thenReturn(null);

        Move move = moveDataAccess.accessMoveData( 1 );
        assertEquals( -1, move.getId() );
    }

    @Test
    public void accessPokemonMovesDataNotFound() {
        when(movesDAO.getPokemonMoves( 1 )).thenReturn(new ArrayList<move_value>());

        List<Move> moves = moveDataAccess.accessPokemonMovesData(1);
        assertTrue( moves.isEmpty() );
    }

    @Test
    public void accessPokemonMovesDataFound() {
        when(typeDataAccess.accessTypeData(1)).thenReturn(Type.ICE);
        ArrayList<move_value> rawMoves = new ArrayList<>();
        rawMoves.add( buildMove() );
        when(movesDAO.getPokemonMoves( 1 )).thenReturn( rawMoves );

        List<Move> moves = moveDataAccess.accessPokemonMovesData( 1 );

        assertEquals( 1, moves.size() );
        assertMove( moves.get(0) );
    }

    @Test(expected = IllegalIDException.class)
    public void accessMoveDataInvalidID0() {
        moveDataAccess.accessMoveData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessMovesDataInvalidIDNegative() {
        moveDataAccess.accessMoveData( -1 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonMovesDataInvalidID0() {
        moveDataAccess.accessPokemonMovesData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonMovesDataInvalidIDNegative() {
        moveDataAccess.accessPokemonMovesData( -1 );
    }

    private void assertMove( Move move ) {
        assertEquals( 1, move.getId() );
        assertEquals( "mock", move.getName() );
        assertEquals( Type.ICE, move.getType() );
        assertEquals( Move.Category.PHYSICAL, move.getCategory() );
        assertEquals( "short_effect", move.getEffect() );
        assertEquals( 100, move.getAccuracy() );
        assertEquals( 100, move.getPower() );
        assertEquals( 100, move.getPp() );
    }

    private move_value buildMove() {
        move_value move = new move_value();
        move.id = 1;
        move.typeId = 1;
        move.name = "mock";
        move.category = "physical";
        move.shortEffect = "short_effect";
        move.effect = "effect";
        move.accuracy = 100;
        move.power = 100;
        move.pp = 100;
        return move;
    }
}