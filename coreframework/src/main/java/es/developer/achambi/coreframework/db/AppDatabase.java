package es.developer.achambi.coreframework.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import es.developer.achambi.coreframework.db.dao.AbilitiesDAO;
import es.developer.achambi.coreframework.db.dao.ConfigurationDAO;
import es.developer.achambi.coreframework.db.dao.ItemDAO;
import es.developer.achambi.coreframework.db.dao.MovesDAO;
import es.developer.achambi.coreframework.db.dao.NaturesDAO;
import es.developer.achambi.coreframework.db.dao.PokemonDAO;
import es.developer.achambi.coreframework.db.dao.StatsDAO;
import es.developer.achambi.coreframework.db.dao.TypeDAO;
import es.developer.achambi.coreframework.db.model.abilities;
import es.developer.achambi.coreframework.db.model.ability_prose;
import es.developer.achambi.coreframework.db.model.configuration_stats;
import es.developer.achambi.coreframework.db.model.configurations;
import es.developer.achambi.coreframework.db.model.item_prose;
import es.developer.achambi.coreframework.db.model.items;
import es.developer.achambi.coreframework.db.model.move_damage_classes;
import es.developer.achambi.coreframework.db.model.move_effect_prose;
import es.developer.achambi.coreframework.db.model.moves;
import es.developer.achambi.coreframework.db.model.natures;
import es.developer.achambi.coreframework.db.model.pokemon_abilities;
import es.developer.achambi.coreframework.db.model.pokemon_moves;
import es.developer.achambi.coreframework.db.model.pokemon;
import es.developer.achambi.coreframework.db.model.pokemon_stats;
import es.developer.achambi.coreframework.db.model.pokemon_types;
import es.developer.achambi.coreframework.db.model.stats;
import es.developer.achambi.coreframework.db.model.types;
import es.developer.achambi.coreframework.db.utils.DatabaseUtils;

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
