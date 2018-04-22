package es.developer.achambi.pkmng.core.db.model;

import android.arch.persistence.room.ColumnInfo;

public class move_value {
    public int id;

    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="category")
    public String category;
    @ColumnInfo(name="short_effect")
    public String shortEffect;
    @ColumnInfo(name="effect")
    public String effect;
    @ColumnInfo(name="type_id")
    public int typeId;
    @ColumnInfo(name="power")
    public int power;
    @ColumnInfo(name="pp")
    public int pp;
    @ColumnInfo(name="accuracy")
    public int accuracy;
}
