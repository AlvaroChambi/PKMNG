package es.developer.achambi.pkmng.modules.search.item.data;


import es.developer.achambi.pkmng.core.db.dao.ItemDAO;

public class ItemDataAccessFactory {
    public ItemDataAccess buildDataAccess(ItemDAO itemDAO) {
        return new ItemDataAccess(itemDAO);
    }
}
