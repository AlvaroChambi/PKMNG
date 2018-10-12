package es.developer.achambi.pkmng.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import es.developer.achambi.coreframework.db.utils.DatabaseUtils;
import es.developer.achambi.pkmng.database.dao.AbilitiesDAO;
import es.developer.achambi.pkmng.database.dao.ConfigurationDAO;
import es.developer.achambi.pkmng.database.dao.ItemDAO;
import es.developer.achambi.pkmng.database.dao.MovesDAO;
import es.developer.achambi.pkmng.database.dao.NaturesDAO;
import es.developer.achambi.pkmng.database.dao.PokemonDAO;
import es.developer.achambi.pkmng.database.dao.StatsDAO;
import es.developer.achambi.pkmng.database.dao.TypeDAO;
import es.developer.achambi.pkmng.database.model.abilities;
import es.developer.achambi.pkmng.database.model.ability_prose;
import es.developer.achambi.pkmng.database.model.configuration_stats;
import es.developer.achambi.pkmng.database.model.configurations;
import es.developer.achambi.pkmng.database.model.items;
import es.developer.achambi.pkmng.database.model.item_prose;
import es.developer.achambi.pkmng.database.model.move_damage_classes;
import es.developer.achambi.pkmng.database.model.move_effect_prose;
import es.developer.achambi.pkmng.database.model.moves;
import es.developer.achambi.pkmng.database.model.natures;
import es.developer.achambi.pkmng.database.model.pokemon;
import es.developer.achambi.pkmng.database.model.pokemon_abilities;
import es.developer.achambi.pkmng.database.model.pokemon_moves;
import es.developer.achambi.pkmng.database.model.pokemon_stats;
import es.developer.achambi.pkmng.database.model.pokemon_types;
import es.developer.achambi.pkmng.database.model.stats;
import es.developer.achambi.pkmng.database.model.types;

@Database(entities = {pokemon.class, pokemon_types.class, pokemon_stats.class,
    stats.class, types.class, items.class, item_prose.class, natures.class,
    abilities.class, ability_prose.class, pokemon_abilities.class, move_damage_classes.class,
    move_effect_prose.class, moves.class, pokemon_moves.class, configurations.class,
    configuration_stats.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "pokedex.sqlite";

    public abstract PokemonDAO pokemonModel();
    public abstract TypeDAO typeModel();
    public abstract StatsDAO statsModel();
    public abstract ItemDAO itemsModel();
    public abstract NaturesDAO naturesModel();
    public abstract AbilitiesDAO abilitiesModel();
    public abstract MovesDAO movesModel();
    public abstract ConfigurationDAO configurationsModel();

    /**
     * On first execution, copies initial database on the database application directory if needed,
     * Database is build after calling to this method
     * @param context
     * @return initialized database
     */
    public static AppDatabase buildDatabase(Context context) {
        try {
            DatabaseUtils.copyDataBase(context, AppDatabase.DB_NAME);
        } catch ( Exception e ){
            e.printStackTrace();
        }
        return  Room.databaseBuilder(
                context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
    }
}
