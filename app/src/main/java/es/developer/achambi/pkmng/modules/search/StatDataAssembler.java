package es.developer.achambi.pkmng.modules.search;

import es.developer.achambi.pkmng.database.dao.StatsDAO;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.data.stat.StatDataAccessFactory;

public class StatDataAssembler {
    private StatsDAO statsDAO;
    private StatDataAccessFactory dataAccessFactory;

    public StatDataAssembler setStatsDAO(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
        return this;
    }

    public StatDataAssembler setDataAccessFactory(StatDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
        return this;
    }

    public IStatDataAccess getStatDataAccess() {
        return dataAccessFactory.buildDataAccess( statsDAO );
    }
}
