package es.developer.achambi.pkmng.core.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StatsDAO {
    @Query("select stat_id, identifier, base_stat from pokemon_stats" +
        " join stats on pokemon_stats.stat_id = stats.id" +
        " where pokemon_id = :id")
    List<stat_value> getStats( int id );
}
