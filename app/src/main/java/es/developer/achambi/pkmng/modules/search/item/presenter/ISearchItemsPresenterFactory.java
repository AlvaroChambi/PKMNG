package es.developer.achambi.pkmng.modules.search.item.presenter;

import es.developer.achambi.pkmng.modules.search.item.screen.ISearchItemScreen;

public interface ISearchItemsPresenterFactory {
    SearchItemsPresenter buildPresenter(ISearchItemScreen screen);
}
