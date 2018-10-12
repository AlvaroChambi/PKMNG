package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.ColumnInfo;

public class item_value {
    public int id;

    @ColumnInfo(name="identifier")
    public String name;
    @ColumnInfo(name="short_effect")
    public String shortEffect;
    @ColumnInfo(name="effect")
    public String effect;
}
