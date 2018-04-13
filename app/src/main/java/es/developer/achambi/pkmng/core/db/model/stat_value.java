package es.developer.achambi.pkmng.core.db.model;

import android.arch.persistence.room.ColumnInfo;

public class stat_value {
    public int stat_id;

    @ColumnInfo(name="identifier")
    public String name;
    @ColumnInfo(name="base_stat")
    public int value;
}
