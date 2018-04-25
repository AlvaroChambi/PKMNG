package es.developer.achambi.pkmng.modules.search.move.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.ITypeDataAccessFactory;

public class MoveDataAccessFactory implements IMoveDataAccessFactory {
    private AppDatabase database;
    private ITypeDataAccessFactory typeDataAccess;

    public MoveDataAccessFactory(AppDatabase database, ITypeDataAccessFactory typeDataAccess) {
        this.database = database;
        this.typeDataAccess = typeDataAccess;
    }

    @Override
    public MoveDataAccess buildDataAccess() {
        return new MoveDataAccess(database,
                typeDataAccess.buildDataAccess());
    }
}
