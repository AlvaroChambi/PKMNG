package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.configurations;

@Dao
public interface ConfigurationDAO {
    @Query("select * from configurations")
    List<configurations> getConfigurations();
    @Insert
    long insert(configurations configuration);
    @Update
    void update(configurations configuration);
}
