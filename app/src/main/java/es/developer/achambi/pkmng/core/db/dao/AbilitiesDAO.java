package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.ability_value;

@Dao
public interface AbilitiesDAO {
    @Query("select abilities.id, abilities.identifier, effect, short_effect " +
            "from pokemon_abilities " +
            "join abilities on abilities.id = pokemon_abilities.ability_id " +
            "join ability_prose on abilities.id = ability_prose.ability_id " +
            "where pokemon_abilities.pokemon_id = :pokemonId" )
    List<ability_value> getPokemonAbilities( int pokemonId );
}
