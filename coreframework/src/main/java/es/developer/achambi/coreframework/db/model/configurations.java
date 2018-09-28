package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class configurations {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String name;
    public int pokemon_id;
    public int item_id;
    public int ability_id;
    public int nature_id;
    public int move_0_id;
    public int move_1_id;
    public int move_2_id;
    public int move_3_id;
}
