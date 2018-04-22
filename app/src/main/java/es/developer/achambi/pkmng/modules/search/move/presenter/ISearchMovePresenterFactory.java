package es.developer.achambi.pkmng.modules.search.move.presenter;

import es.developer.achambi.pkmng.modules.search.move.screen.ISearchMoveScreen;

public interface ISearchMovePresenterFactory {
    SearchMovePresenter buildPresenter(ISearchMoveScreen screen);
}
