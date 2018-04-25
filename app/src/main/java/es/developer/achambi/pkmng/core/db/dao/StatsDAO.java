package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.stat_value;
import es.developer.achambi.pkmng.core.db.model.stats;

@Dao
public interface StatsDAO {
    @Query("select stat_id, identifier, base_stat from pokemon_stats" +
        " join stats on pokemon_stats.stat_id = stats.id" +
        " where pokemon_id = :id")
    List<stat_value> getStats( int id );

    @Query("select stat_id, identifier, configuration_stats.ev_value base_stat from configuration_stats" +
            " join stats on configuration_stats.stat_id = stats.id" +
            " where configuration_id = :evsId")
    List<stat_value> getEvsSet( int evsId );

    @Query("select id, identifier from stats " +
            "where id = :id")
    stats getStat( int id );
}
