package es.developer.achambi.pkmng.modules.search.nature.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.coreframework.db.dao.NaturesDAO;
import es.developer.achambi.coreframework.db.model.natures;
import es.developer.achambi.coreframework.exception.IllegalIDException;
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
        return buildNaturesList( rawNatures );
    }

    @Override
    public ArrayList<Nature> accessNatureQueryData( String query ) {
        if( query == null ) {
            return new ArrayList<>();
        }
        List<natures> rawNatures = naturesDAO.queryNatures( "%" + query + "%" );
        return buildNaturesList( rawNatures );
    }

    @Override
    public Nature accessNatureData(int natureId) throws IllegalIDException {
        if( natureId == -1 ) {
            return new Nature();
        }
        if( natureId < 1 ) {
            throw new IllegalIDException( natureId );
        }
        natures rawNature = naturesDAO.getNature(natureId);
        return buildNature( rawNature );
    }

    private ArrayList<Nature> buildNaturesList( List<natures> rawNatures ) {
        ArrayList<Nature> naturesList = new ArrayList<>(rawNatures.size());
        for ( natures rawNature : rawNatures ) {
            naturesList.add( buildNature( rawNature ) );
        }
        return naturesList;
    }

    private Nature buildNature( natures rawNature ) {
        Nature nature = new Nature();
        if( rawNature == null ) {
            return nature;
        }
        nature.setId( rawNature.id );
        nature.setName( rawNature.identifier );
        if( rawNature.increased_stat_id != rawNature.decreased_stat_id ) {
            nature.setIncreasedStat( statDataAccess.accessStatData( rawNature.increased_stat_id ) );
            nature.setDecreasedStat( statDataAccess.accessStatData( rawNature.decreased_stat_id ) );
        }
        return nature;
    }
}
