package es.developer.achambi.pkmng.modules.search;

import es.developer.achambi.pkmng.core.db.dao.ItemDAO;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;

public class ItemDataAssembler {
    private ItemDAO itemDAO;
    private ItemDataAccessFactory dataAccessFactory;

    public ItemDataAssembler setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
        return this;
    }

    public ItemDataAssembler setDataAccessFactory(ItemDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
        return this;
    }

    public IItemDataAccess getDataAccess() {
        return dataAccessFactory.buildDataAccess( itemDAO );
    }
}
