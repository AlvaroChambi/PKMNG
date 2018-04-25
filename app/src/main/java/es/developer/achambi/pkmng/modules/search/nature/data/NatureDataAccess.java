package es.developer.achambi.pkmng.modules.search.nature.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.natures;
import es.developer.achambi.pkmng.modules.data.StatDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class NatureDataAccess {
    private AppDatabase database;
    private StatDataAccess statDataAccess;

    public NatureDataAccess( AppDatabase database,
                             StatDataAccess statDataAccess ) {
        this.database = database;
        this.statDataAccess = statDataAccess;
    }

    public ArrayList<Nature> accessData() {
        List<natures> rawNatures = database.naturesModel().getNatures();
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

    public Nature accessNatureDate(int natureId) {
        natures rawNature = database.naturesModel().getNature(natureId);
        Nature nature = new Nature();
        nature.setId( rawNature.id );
        nature.setName( rawNature.identifier );
        nature.setIncreasedStat( statDataAccess.accessStatData( rawNature.increased_stat_id ) );
        nature.setDecreasedStat( statDataAccess.accessStatData( rawNature.decreased_stat_id ) );
        return nature;
    }
}
