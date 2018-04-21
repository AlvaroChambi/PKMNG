package es.developer.achambi.pkmng.modules.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;

public class StatDataAccessFactory implements IStatDataAccessFactory {
    private AppDatabase database;

    public StatDataAccessFactory(AppDatabase database) {
        this.database = database;
    }

    @Override
    public StatDataAccess buildDataAccess() {
        return new StatDataAccess( database );
    }
}
