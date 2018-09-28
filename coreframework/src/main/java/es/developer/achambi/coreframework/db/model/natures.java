package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class natures {
    @PrimaryKey
    @NonNull
    public int id;
    public String identifier;
    public int increased_stat_id;
    public int decreased_stat_id;
}
