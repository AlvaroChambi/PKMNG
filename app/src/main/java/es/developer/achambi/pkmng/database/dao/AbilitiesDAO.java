package es.developer.achambi.pkmng.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.database.model.ability_value;

@Dao
public interface AbilitiesDAO {
    @Query("select abilities.id, abilities.identifier, effect, short_effect " +
            "from pokemon_abilities " +
            "join abilities on abilities.id = pokemon_abilities.ability_id " +
            "join ability_prose on abilities.id = ability_prose.ability_id " +
            "where pokemon_abilities.pokemon_id = :pokemonId" )
    List<ability_value> getPokemonAbilities( int pokemonId );

    @Query("select abilities.id, abilities.identifier, effect, short_effect " +
            "from pokemon_abilities " +
            "join abilities on abilities.id = pokemon_abilities.ability_id " +
            "join ability_prose on abilities.id = ability_prose.ability_id " +
            "where pokemon_abilities.pokemon_id = :pokemonId " +
            "and abilities.identifier like :query" )
    List<ability_value> getAbilitiesQuery( int pokemonId, String query );

    @Query("select abilities.id, abilities.identifier, effect, short_effect " +
            "from abilities " +
            "join ability_prose on abilities.id = ability_prose.ability_id " +
            "where abilities.id = :abilityId" )
    ability_value getAbility( int abilityId );
}
