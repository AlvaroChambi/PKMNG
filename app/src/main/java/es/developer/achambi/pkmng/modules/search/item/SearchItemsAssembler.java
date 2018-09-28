package es.developer.achambi.pkmng.modules.search.item;

import es.developer.achambi.coreframework.db.AppDatabase;
import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.ItemDataAssembler;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.item.presenter.ISearchItemsPresenterFactory;
import es.developer.achambi.pkmng.modules.search.item.presenter.SearchItemsPresenterFactory;

public class SearchItemsAssembler {
    private MainExecutor executor;
    private ItemDataAssembler itemDataAssembler;

    public SearchItemsAssembler setItemDataAssembler(ItemDataAssembler itemDataAssembler) {
        this.itemDataAssembler = itemDataAssembler;
        return this;
    }

    public SearchItemsAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public ISearchItemsPresenterFactory getPresenterFactory() {
        return new SearchItemsPresenterFactory(itemDataAssembler.getDataAccess(), executor );
    }
}
