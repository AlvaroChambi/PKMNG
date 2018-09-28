package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class pokemon_types {
    @PrimaryKey
    @NonNull
    public int pokemon_id;
    public int type_id;
}
