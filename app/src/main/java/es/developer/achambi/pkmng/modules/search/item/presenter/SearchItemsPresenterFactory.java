package es.developer.achambi.pkmng.modules.search.item.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.item.screen.ISearchItemScreen;

public class SearchItemsPresenterFactory implements ISearchItemsPresenterFactory{
    private IItemDataAccess dataAccess;
    private MainExecutor executor;

    public SearchItemsPresenterFactory( IItemDataAccess dataAccess,
                                        MainExecutor executor ) {
        this.dataAccess = dataAccess;
        this.executor = executor;
    }

    @Override
    public SearchItemsPresenter buildPresenter(ISearchItemScreen screen) {
        return new SearchItemsPresenter( screen, dataAccess, executor );
    }
}
