package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class move_damage_classes {
    @PrimaryKey
    @NonNull
    public int id;
    public int identifier;
}
