package es.developer.achambi.pkmng.modules.search.item.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.search.item.model.Item;

public interface IItemDataAccess {
    ArrayList<Item> accessData();
    Item accessItemData(int itemId);
}
