package es.developer.achambi.pkmng.modules.search.move.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.move.screen.ISearchMoveScreen;

public class SearchMovePresenterFactory implements ISearchMovePresenterFactory{
    private IMoveDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public SearchMovePresenterFactory(IMoveDataAccessFactory dataAccessFactory,
                                      MainExecutor executor) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    @Override
    public SearchMovePresenter buildPresenter(ISearchMoveScreen screen) {
        return new SearchMovePresenter(screen, dataAccessFactory.buildDataAccess(), executor);
    }
}
