package es.developer.achambi.pkmng.core.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import es.developer.achambi.pkmng.core.db.model.item_value;

@Dao
public interface ItemDAO {
    @Query("select id, identifier, short_effect, effect " +
           "from items " +
           "join item_prose on items.id = item_prose.item_id" )
    List<item_value> getItems();

    @Query("select id, identifier, short_effect, effect " +
            "from items " +
            "join item_prose on items.id = item_prose.item_id " +
            "where id = :itemId")
    item_value getItem(int itemId);
}
