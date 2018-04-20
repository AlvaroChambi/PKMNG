package es.developer.achambi.pkmng.modules.search.item.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.item_value;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemDataAccess {
    private AppDatabase database;

    public ItemDataAccess( AppDatabase database ) {
        this.database = database;
    }

    public ArrayList<Item> accessData() {
        List<item_value> itemsArray = database.itemsModel().getItems();
        ArrayList<Item> items = new ArrayList<>( itemsArray.size() );
        for( item_value currentItem : itemsArray ) {
            Item item = new Item();
            item.setId( currentItem.id );
            item.setName( currentItem.name );
            item.setDescriptionShort( currentItem.shortEffect );
            item.setDescription( currentItem.effect );
            items.add(item);
        }
        return items;
    }
}
