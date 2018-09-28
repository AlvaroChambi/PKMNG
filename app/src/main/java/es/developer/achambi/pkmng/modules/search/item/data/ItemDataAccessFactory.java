package es.developer.achambi.pkmng.modules.search.item.data;


import es.developer.achambi.coreframework.db.dao.ItemDAO;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;

public class ItemDataAccessFactory {
    public ItemDataAccess buildDataAccess(ItemDAO itemDAO, DataFormatUtil formatter) {
        return new ItemDataAccess(itemDAO, formatter);
    }
}
