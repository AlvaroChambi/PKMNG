package es.developer.achambi.pkmng.modules.search.move.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccess;
import es.developer.achambi.pkmng.modules.search.move.screen.ISearchMoveScreen;

public class SearchMovePresenterFactory implements ISearchMovePresenterFactory{
    private IMoveDataAccess moveDataAccess;
    private MainExecutor executor;

    public SearchMovePresenterFactory( MainExecutor executor,
                                       IMoveDataAccess moveDataAccess ) {
        this.moveDataAccess = moveDataAccess;
        this.executor = executor;
    }

    @Override
    public SearchMovePresenter buildPresenter(ISearchMoveScreen screen) {
        return new SearchMovePresenter(screen, moveDataAccess, executor);
    }
}
