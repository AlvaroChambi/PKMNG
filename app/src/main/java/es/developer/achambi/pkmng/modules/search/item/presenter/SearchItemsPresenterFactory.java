package es.developer.achambi.pkmng.modules.search.item.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.item.screen.ISearchItemScreen;

public class SearchItemsPresenterFactory implements ISearchItemsPresenterFactory{
    private IItemDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public SearchItemsPresenterFactory( IItemDataAccessFactory dataAccessFactory,
                                        MainExecutor executor ) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    @Override
    public SearchItemsPresenter buildPresenter(ISearchItemScreen screen) {
        return new SearchItemsPresenter( screen, dataAccessFactory.buildDataAccess(), executor );
    }
}
