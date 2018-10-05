package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class moves {
    @PrimaryKey
    @NonNull
    public int id;
    public String identifier;
    public int damage_class_id;
    public int effect_id;
    public int type_id;
    public int power;
    public int pp;
    public int accuracy;
}
