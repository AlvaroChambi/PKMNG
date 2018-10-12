package es.developer.achambi.pkmng.modules.search.move;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.MoveDataAssembler;
import es.developer.achambi.pkmng.modules.search.move.presenter.ISearchMovePresenterFactory;
import es.developer.achambi.pkmng.modules.search.move.presenter.SearchMovePresenterFactory;

public class SearchMoveAssembler {
    private MainExecutor executor;
    private MoveDataAssembler moveDataAssembler;

    public SearchMoveAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public SearchMoveAssembler setMoveDataAssembler(MoveDataAssembler moveDataAssembler) {
        this.moveDataAssembler = moveDataAssembler;
        return this;
    }

    public ISearchMovePresenterFactory getPresenterFactory() {
        return new SearchMovePresenterFactory( executor, moveDataAssembler.getMoveDataAccess() );
    }
}
