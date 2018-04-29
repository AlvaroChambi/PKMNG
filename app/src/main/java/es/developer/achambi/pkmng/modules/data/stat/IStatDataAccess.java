package es.developer.achambi.pkmng.modules.data.stat;

import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public interface IStatDataAccess {
    StatsSet accessPokemonStatsData( int pokemonId );
    StatsSet accessEvsSetData( int configurationId );
    Stat accessStatData(int statId );
    void insertStatsSet( int configurationId, StatsSet statsSet );
    void updateStatsSet( int configurationId, StatsSet StatsSet );
}
