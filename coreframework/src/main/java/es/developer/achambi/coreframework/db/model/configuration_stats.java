package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;

@Entity(primaryKeys = {"configuration_id", "stat_id"})
public class configuration_stats {
    public int configuration_id;
    public int stat_id;
    public int ev_value;
}
