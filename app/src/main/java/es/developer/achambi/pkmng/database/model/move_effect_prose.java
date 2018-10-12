package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class move_effect_prose {
    @PrimaryKey
    @NonNull
    public int move_effect_id;
    public String short_effect;
    public String effect;
}
