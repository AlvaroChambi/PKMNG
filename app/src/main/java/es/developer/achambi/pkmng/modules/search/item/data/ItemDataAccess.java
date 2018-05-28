package es.developer.achambi.pkmng.modules.search.item.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.ItemDAO;
import es.developer.achambi.pkmng.core.db.model.item_value;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemDataAccess implements IItemDataAccess{
    private ItemDAO itemDAO;

    private ArrayList<Item> cachedData;

    public ItemDataAccess( ItemDAO itemDAO ) {
        this.itemDAO = itemDAO;
    }

    @Override
    public ArrayList<Item> accessData() {
        if(cachedData != null) {
            return cachedData;
        }
        List<item_value> rawItems = itemDAO.getItems();
        cachedData = buildItemsList( rawItems );
        return cachedData;
    }

    @Override
    public ArrayList<Item> queryItemData(String query) {
        if( query == null ) {
            return new ArrayList<>();
        }
        List<item_value> rawItems = itemDAO.queryItems( query + "%" );
        return buildItemsList( rawItems );
    }

    @Override
    public Item accessItemData(int itemId) throws IllegalIDException {
        if( itemId == -1 ) {
            return new Item();
        }
        if( itemId < 1 ) {
            throw new IllegalIDException( itemId );
        }
        item_value rawItem = itemDAO.getItem(itemId);
        Item item = new Item();
        if(rawItem != null) {
            item.setId( rawItem.id );
            item.setName( rawItem.name );
            item.setDescriptionShort( rawItem.shortEffect );
            item.setDescription( rawItem.effect );
        }
        return item;
    }

    private ArrayList<Item> buildItemsList( List<item_value> rawItems ) {
        ArrayList<Item> items = new ArrayList<>( rawItems.size() );
        for( item_value currentItem : rawItems ) {
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
