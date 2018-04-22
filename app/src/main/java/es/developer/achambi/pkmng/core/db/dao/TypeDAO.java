package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.type_value;
import es.developer.achambi.pkmng.core.db.model.types;

@Dao
public interface TypeDAO {
    @Query("select type_id, identifier from pokemon_types" +
            " join types on pokemon_types.type_id = types.id" +
            " where pokemon_id = :id")
    List<type_value> getPokemonType(int id );

    @Query("select * from types " +
            "where id = :typeId")
    types getType(int typeId );
}
