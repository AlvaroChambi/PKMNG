package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class stats {
    @PrimaryKey
    @NonNull
    public int id;
    public String identifier;
}
