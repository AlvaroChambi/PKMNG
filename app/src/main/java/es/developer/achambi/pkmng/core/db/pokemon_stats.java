package es.developer.achambi.pkmng.core.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class pokemon_stats {
    @PrimaryKey
    @NonNull
    public int pokemon_id;
    public int stat_id;
    public int base_stat;
}
