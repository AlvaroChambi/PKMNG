package es.developer.achambi.pkmng.modules.data.stat;

import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public interface IStatDataAccess {
    StatsSet accessPokemonStatsData( int pokemonId ) throws IllegalIDException;
    StatsSet accessEvsSetData( int configurationId ) throws IllegalIDException;
    Stat accessStatData(int statId ) throws IllegalIDException;
    void insertStatsSet( int configurationId, StatsSet statsSet ) throws IllegalArgumentException;
    void updateStatsSet( int configurationId, StatsSet statsSet ) throws IllegalArgumentException;
}
