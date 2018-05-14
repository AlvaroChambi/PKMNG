package es.developer.achambi.pkmng.modules.search.nature.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.NaturesDAO;
import es.developer.achambi.pkmng.core.db.model.natures;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class NatureDataAccess implements INatureDataAccess {
    private NaturesDAO naturesDAO;
    private IStatDataAccess statDataAccess;

    public NatureDataAccess( NaturesDAO naturesDAO,
                             IStatDataAccess statDataAccess ) {
        this.naturesDAO = naturesDAO;
        this.statDataAccess = statDataAccess;
    }

    @Override
    public ArrayList<Nature> accessData() {
        List<natures> rawNatures = naturesDAO.getNatures();
        ArrayList<Nature> naturesList = new ArrayList<>(rawNatures.size());
        for ( natures rawNature : rawNatures ) {
            Nature nature = new Nature();
            nature.setId( rawNature.id );
            nature.setName( rawNature.identifier );
            nature.setIncreasedStat( statDataAccess.accessStatData( rawNature.increased_stat_id ) );
            nature.setDecreasedStat( statDataAccess.accessStatData( rawNature.decreased_stat_id ) );

            naturesList.add(nature);
        }
        return naturesList;
    }

    @Override
    public Nature accessNatureData(int natureId) throws IllegalIDException {
        if( natureId < 1 ) {
            throw new IllegalIDException( natureId );
        }
        natures rawNature = naturesDAO.getNature(natureId);
        Nature nature = new Nature();
        if(rawNature != null) {
            nature.setId( rawNature.id );
            nature.setName( rawNature.identifier );
            nature.setIncreasedStat( statDataAccess.accessStatData( rawNature.increased_stat_id ) );
            nature.setDecreasedStat( statDataAccess.accessStatData( rawNature.decreased_stat_id ) );
        }
        return nature;
    }
}
