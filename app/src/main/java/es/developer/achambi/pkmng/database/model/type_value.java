package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.ColumnInfo;

public class type_value {
    public int type_id;

    @ColumnInfo(name="identifier")
    public String name;
}
