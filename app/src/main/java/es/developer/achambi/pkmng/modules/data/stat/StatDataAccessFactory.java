package es.developer.achambi.pkmng.modules.data.stat;

import es.developer.achambi.pkmng.database.dao.StatsDAO;

public class StatDataAccessFactory {
    public IStatDataAccess buildDataAccess(StatsDAO statsDAO) {
        return new StatDataAccess( statsDAO );
    }
}
