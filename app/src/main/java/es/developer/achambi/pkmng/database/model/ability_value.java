package es.developer.achambi.pkmng.database.model;

import android.arch.persistence.room.ColumnInfo;

public class ability_value {
    public int id;

    @ColumnInfo(name="identifier")
    public String name;
    @ColumnInfo(name="effect")
    public String effect;
    @ColumnInfo(name="short_effect")
    public String shortEffect;
}
