package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.move_value;

@Dao
public interface MovesDAO {
    @Query("select moves.id, moves.identifier name, move_damage_classes.identifier category, power, pp, accuracy," +
            "short_effect, effect, type_id from pokemon_moves " +
            "join moves on moves.id = pokemon_moves.move_id " +
            "join move_damage_classes on move_damage_classes.id = moves.damage_class_id " +
            "join move_effect_prose on moves.effect_id = move_effect_prose.move_effect_id " +
            "where pokemon_moves.pokemon_id = :pokemonId")
    public List<move_value> getPokemonMoves( int pokemonId );
}
