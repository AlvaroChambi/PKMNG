package es.developer.achambi.pkmng.core.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {pokemon_species.class, pokemon_types.class, pokemon_stats.class,
    stats.class, types.class },version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "pokedex.sqlite";
    public static AppDatabase INSTANCE;

    public abstract PokemonDAO pokemonModel();
    public abstract TypeDAO typeModel();
    public abstract StatsDAO statsModel();

    public static AppDatabase buildDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
        }
        return INSTANCE;
    }
    public static AppDatabase buildDatabase() {
        return INSTANCE;
    }
}
