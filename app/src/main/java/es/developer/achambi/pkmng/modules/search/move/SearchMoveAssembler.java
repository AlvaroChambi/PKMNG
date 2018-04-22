package es.developer.achambi.pkmng.modules.search.move;

import es.developer.achambi.pkmng.modules.search.move.presenter.ISearchMovePresenterFactory;

public class SearchMoveAssembler {
    private ISearchMovePresenterFactory presenterFactory;

    public ISearchMovePresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ISearchMovePresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
