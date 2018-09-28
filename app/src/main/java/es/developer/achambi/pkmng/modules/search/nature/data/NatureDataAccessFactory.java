package es.developer.achambi.pkmng.modules.search.nature.data;

import es.developer.achambi.coreframework.db.dao.NaturesDAO;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;

public class NatureDataAccessFactory {
    public NatureDataAccess buildDataAccess(NaturesDAO naturesDAO, IStatDataAccess statDataAccess) {
        return new NatureDataAccess(naturesDAO, statDataAccess);
    }
}
