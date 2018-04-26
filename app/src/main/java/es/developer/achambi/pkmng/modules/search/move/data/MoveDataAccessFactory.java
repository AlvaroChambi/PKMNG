package es.developer.achambi.pkmng.modules.search.move.data;

import es.developer.achambi.pkmng.core.db.dao.MovesDAO;
import es.developer.achambi.pkmng.modules.data.ITypeDataAccessFactory;

public class MoveDataAccessFactory implements IMoveDataAccessFactory {
    private MovesDAO movesDAO;
    private ITypeDataAccessFactory typeDataAccess;

    public MoveDataAccessFactory(MovesDAO movesDAO, ITypeDataAccessFactory typeDataAccess) {
        this.movesDAO = movesDAO;
        this.typeDataAccess = typeDataAccess;
    }

    @Override
    public MoveDataAccess buildDataAccess() {
        return new MoveDataAccess(movesDAO, typeDataAccess.buildDataAccess());
    }
}
