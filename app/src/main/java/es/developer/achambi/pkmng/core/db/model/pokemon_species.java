package es.developer.achambi.pkmng.core.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class pokemon_species {
    @PrimaryKey
    @NonNull
    public int id;
    public String identifier;
}
