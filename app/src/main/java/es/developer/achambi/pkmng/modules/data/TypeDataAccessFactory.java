package es.developer.achambi.pkmng.modules.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;

public class TypeDataAccessFactory implements ITypeDataAccessFactory {
    private AppDatabase database;

    public TypeDataAccessFactory(AppDatabase database) {
        this.database = database;
    }

    @Override
    public TypeDataAccess buildDataAccess() {
        return new TypeDataAccess( database );
    }
}
