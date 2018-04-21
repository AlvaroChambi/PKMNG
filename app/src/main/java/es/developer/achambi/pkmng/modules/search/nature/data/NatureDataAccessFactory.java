package es.developer.achambi.pkmng.modules.search.nature.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.IStatDataAccessFactory;

public class NatureDataAccessFactory implements INatureDataAccessFactory{
    private AppDatabase database;
    private IStatDataAccessFactory statDataAccessFactory;

    public NatureDataAccessFactory(AppDatabase database, IStatDataAccessFactory statDataAccessFactory) {
        this.database = database;
        this.statDataAccessFactory = statDataAccessFactory;
    }

    @Override
    public NatureDataAccess buildDataAccess() {
        return new NatureDataAccess(database, statDataAccessFactory.buildDataAccess());
    }
}
