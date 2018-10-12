package es.developer.achambi.pkmng.modules.search;

import es.developer.achambi.pkmng.database.dao.NaturesDAO;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccessFactory;

public class NatureDataAssembler {
    private NaturesDAO naturesDAO;
    private StatDataAssembler statDataAssembler;
    private NatureDataAccessFactory natureDataAccessFactory;

    public NatureDataAssembler setNaturesDAO(NaturesDAO naturesDAO) {
        this.naturesDAO = naturesDAO;
        return this;
    }

    public NatureDataAssembler setStatDataAssembler(StatDataAssembler statDataAssembler) {
        this.statDataAssembler = statDataAssembler;
        return this;
    }

    public NatureDataAssembler setNatureDataAccessFactory(
            NatureDataAccessFactory natureDataAccessFactory) {
        this.natureDataAccessFactory = natureDataAccessFactory;
        return this;
    }

    public INatureDataAccess getNatureDataAccess() {
        return natureDataAccessFactory.buildDataAccess( naturesDAO,
                statDataAssembler.getStatDataAccess() );
    }
}
