package es.developer.achambi.pkmng.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.database.model.natures;

@Dao
public interface NaturesDAO {
    @Query("select id, identifier, increased_stat_id, decreased_stat_id from natures ")
    List<natures> getNatures();

    @Query("select id, identifier, increased_stat_id, decreased_stat_id from natures " +
            "where identifier like :query" )
    List<natures> queryNatures( String query );

    @Query("select id, identifier, increased_stat_id, decreased_stat_id from natures " +
            "where id = :natureId")
    natures getNature(int natureId);
}
