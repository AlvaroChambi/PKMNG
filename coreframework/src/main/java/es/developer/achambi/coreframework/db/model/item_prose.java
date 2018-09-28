package es.developer.achambi.coreframework.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class item_prose {
    @PrimaryKey
    @NonNull
    public int item_id;
    public String short_effect;
    public String effect;
}
