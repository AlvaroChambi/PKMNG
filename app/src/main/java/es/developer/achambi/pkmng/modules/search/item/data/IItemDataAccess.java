package es.developer.achambi.pkmng.modules.search.item.data;

import java.util.ArrayList;

import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public interface IItemDataAccess {
    /**
     * Access the Items data
     * @return List of Items data
     */
    ArrayList<Item> accessData();

    /**
     * Access the items data that matches the given query
     * @param query text to perform the query, on a null query an empty result will be returned
     * @return available data that starts with the given query
     */
    ArrayList<Item> queryItemData( String query );

    /**
     * Access Item data for the given item id
     * @param itemId of the request Item, id should be higher than 0
     * @return Item data matching the given id, an empty Item if the id doesn't exist or
     * the id is equals to -1
     * @throws IllegalIDException on 0 or negative item id's
     */
    Item accessItemData(int itemId) throws IllegalIDException;
}
