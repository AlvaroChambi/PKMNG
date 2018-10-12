package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class items {
    @PrimaryKey
    @NonNull
    public int id;
    public String identifier;
}
