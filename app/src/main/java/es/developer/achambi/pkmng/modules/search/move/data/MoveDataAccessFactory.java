package es.developer.achambi.pkmng.modules.search.move.data;

import es.developer.achambi.pkmng.database.dao.MovesDAO;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;

public class MoveDataAccessFactory {
    public MoveDataAccess buildDataAccess(MovesDAO movesDAO, ITypeDataAccess typeDataAccess,
                                          DataFormatUtil formatter) {
        return new MoveDataAccess(movesDAO, typeDataAccess, formatter);
    }
}
