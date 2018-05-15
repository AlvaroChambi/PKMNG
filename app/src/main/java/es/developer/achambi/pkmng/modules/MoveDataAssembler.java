package es.developer.achambi.pkmng.modules;

import es.developer.achambi.pkmng.core.db.dao.MovesDAO;
import es.developer.achambi.pkmng.modules.search.TypeDataAssembler;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccess;
import es.developer.achambi.pkmng.modules.search.move.data.MoveDataAccessFactory;

public class MoveDataAssembler {
    private MovesDAO movesDAO;
    private TypeDataAssembler typeDataAssembler;
    private MoveDataAccessFactory moveDataAccessFactory;

    public MoveDataAssembler setMovesDAO(MovesDAO movesDAO) {
        this.movesDAO = movesDAO;
        return this;
    }

    public MoveDataAssembler setTypeDataAssembler(TypeDataAssembler typeDataAssembler) {
        this.typeDataAssembler = typeDataAssembler;
        return this;
    }

    public MoveDataAssembler setMoveDataAccessFactory(MoveDataAccessFactory moveDataAccessFactory) {
        this.moveDataAccessFactory = moveDataAccessFactory;
        return this;
    }

    public IMoveDataAccess getMoveDataAccess() {
        return moveDataAccessFactory.buildDataAccess( movesDAO, typeDataAssembler.getDataAccess() );
    }
}
