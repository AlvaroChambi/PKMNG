package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ability_prose {
    @PrimaryKey
    @NonNull
    public int ability_id;
    public String effect;
    public String short_effect;
}
