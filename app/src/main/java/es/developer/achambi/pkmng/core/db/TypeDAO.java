package es.developer.achambi.pkmng.core.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TypeDAO {
    @Query("select type_id, identifier from pokemon_types" +
            " join types on pokemon_types.type_id = types.id" +
            " where pokemon_id = :id")
    List<type_value> getType(int id );
}
