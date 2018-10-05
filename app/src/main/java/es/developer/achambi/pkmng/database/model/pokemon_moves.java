package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class pokemon_moves {
    @PrimaryKey
    @NonNull
    public int pokemon_id;
    public int move_id;
}
