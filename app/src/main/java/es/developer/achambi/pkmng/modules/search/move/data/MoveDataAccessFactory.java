package es.developer.achambi.pkmng.modules.search.move.data;

import es.developer.achambi.pkmng.core.db.dao.MovesDAO;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccessFactory;

public class MoveDataAccessFactory {
    public MoveDataAccess buildDataAccess(MovesDAO movesDAO, ITypeDataAccess typeDataAccess) {
        return new MoveDataAccess(movesDAO, typeDataAccess);
    }
}
