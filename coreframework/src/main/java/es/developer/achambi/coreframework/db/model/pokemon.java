package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class pokemon {
    @PrimaryKey
    @NonNull
    public int id;
    public String identifier;
    public int species_id;
}
