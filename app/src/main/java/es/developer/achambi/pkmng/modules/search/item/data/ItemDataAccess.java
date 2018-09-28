package es.developer.achambi.pkmng.modules.search.item.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.coreframework.db.dao.ItemDAO;
import es.developer.achambi.coreframework.db.model.item_value;
import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemDataAccess implements IItemDataAccess{
    private ItemDAO itemDAO;
    private DataFormatUtil formatter;
    private ArrayList<Item> cachedData;

    public ItemDataAccess( ItemDAO itemDAO, DataFormatUtil formatter ) {
        this.itemDAO = itemDAO;
        this.formatter = formatter;
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
        List<item_value> rawItems = itemDAO.queryItems( "%" + query + "%" );
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
        return buildItem( rawItem );
    }

    private ArrayList<Item> buildItemsList( List<item_value> rawItems ) {
        ArrayList<Item> items = new ArrayList<>( rawItems.size() );
        for( item_value currentItem : rawItems ) {
            items.add(buildItem( currentItem ));
        }
        return items;
    }

    private Item buildItem( item_value rawItem ) {
        Item item = new Item();
        if(rawItem == null) {
            return item;
        }
        item.setId( rawItem.id );
        item.setName( rawItem.name );
        item.setDescriptionShort( formatter.formatDescriptionMessage(rawItem.shortEffect) );
        item.setDescription( formatter.formatDescriptionMessage(rawItem.effect) );
        return item;
    }
}
