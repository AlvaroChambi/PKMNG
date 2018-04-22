package es.developer.achambi.pkmng.modules.search.move.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.TypeDataAccess;

public class MoveDataAccessFactory implements IMoveDataAccessFactory {
    private AppDatabase database;
    private TypeDataAccess typeDataAccess;

    public MoveDataAccessFactory(AppDatabase database, TypeDataAccess typeDataAccess) {
        this.database = database;
        this.typeDataAccess = typeDataAccess;
    }

    @Override
    public MoveDataAccess buildDataAccess() {
        return new MoveDataAccess(database, typeDataAccess);
    }
}
