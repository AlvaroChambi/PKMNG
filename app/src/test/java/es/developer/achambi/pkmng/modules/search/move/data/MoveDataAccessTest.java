package es.developer.achambi.pkmng.modules.search.move.data;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.dao.MovesDAO;
import es.developer.achambi.pkmng.core.db.model.move_value;

import static org.junit.Assert.*;

public class MoveDataAccessTest {
    private MovesDAO movesDAO;
    @Before
    public void setup() {
        movesDAO = new MovesDAO() {
            @Override
            public List<move_value> getPokemonMoves(int pokemonId) {
                return null;
            }

            @Override
            public move_value getMove(int moveId) {
                return null;
            }
        };
    }

    @Test
    public void accessPokemonMovesDataValidID() {

    }

    @Test
    public void accessPokemonMovesDataInvalidID() {

    }

    @Test
    public void accessMoveData() {
    }
}