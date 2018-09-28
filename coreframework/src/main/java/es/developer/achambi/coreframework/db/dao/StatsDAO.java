package es.developer.achambi.coreframework.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.developer.achambi.coreframework.db.model.configuration_stats;
import es.developer.achambi.coreframework.db.model.stat_value;
import es.developer.achambi.coreframework.db.model.stats;

@Dao
public interface StatsDAO {
    @Query("select stat_id, identifier, base_stat from pokemon_stats" +
        " join stats on pokemon_stats.stat_id = stats.id" +
        " where pokemon_id = :id")
    List<stat_value> getStats( int id );

    @Query("select stat_id, identifier, configuration_stats.ev_value base_stat from configuration_stats" +
            " join stats on configuration_stats.stat_id = stats.id" +
            " where configuration_id = :configurationId")
    List<stat_value> getEvsSet( int configurationId );

    @Query("select id, identifier from stats " +
            "where id = :id")
    stats getStat( int id );

    @Insert
    void insertStatsSet(List<configuration_stats> evs);

    @Update
    void updateStatsSet(List<configuration_stats> evs);
}
