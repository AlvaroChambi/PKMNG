package es.developer.achambi.pkmng.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.developer.achambi.pkmng.database.model.configurations;

@Dao
public interface ConfigurationDAO {
    @Query("select * from configurations")
    List<configurations> getConfigurations();
    @Query("select * from configurations where name like :query")
    List<configurations> queryConfigurations(String query);

    @Insert
    long insert(configurations configuration);
    @Update
    void update(configurations configuration);
}
