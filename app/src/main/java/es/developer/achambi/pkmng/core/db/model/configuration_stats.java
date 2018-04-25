package es.developer.achambi.pkmng.core.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class configuration_stats {
    @PrimaryKey
    @NonNull
    public int configuration_id;
    public int stat_id;
    public int ev_value;
}
