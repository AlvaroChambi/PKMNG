package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.type_value;

@Dao
public interface TypeDAO {
    @Query("select type_id, identifier from pokemon_types" +
            " join types on pokemon_types.type_id = types.id" +
            " where pokemon_id = :id")
    List<type_value> getType(int id );
}
