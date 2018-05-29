package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.pokemon;

@Dao
public interface PokemonDAO {
    @Query("select pokemon.id, pokemon.identifier from pokemon " )
    List<pokemon> getPokemon();

    @Query("select id, identifier from pokemon where identifier like :query")
    List<pokemon> getPokemon(String query );

    @Query("select id, identifier from pokemon where id=:pokemonId")
    pokemon getPokemon(int pokemonId);
}
