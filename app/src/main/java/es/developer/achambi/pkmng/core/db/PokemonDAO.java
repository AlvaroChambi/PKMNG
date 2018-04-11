package es.developer.achambi.pkmng.core.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PokemonDAO {
    @Query("select id, identifier from pokemon_species")
    List<pokemon_species> getPokemon();
}
