package es.developer.achambi.pkmng.modules.search;

import es.developer.achambi.coreframework.db.dao.ItemDAO;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;

public class ItemDataAssembler {
    private ItemDAO itemDAO;
    private ItemDataAccessFactory dataAccessFactory;
    private DataFormatUtil formatter;

    public ItemDataAssembler setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
        return this;
    }

    public ItemDataAssembler setDataAccessFactory(ItemDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
        return this;
    }

    public ItemDataAssembler setFormatter(DataFormatUtil formatter) {
        this.formatter = formatter;
        return this;
    }

    public IItemDataAccess getDataAccess() {
        return dataAccessFactory.buildDataAccess( itemDAO, formatter );
    }
}
