package es.developer.achambi.pkmng.modules.search.item;

import es.developer.achambi.pkmng.modules.search.item.presenter.ISearchItemsPresenterFactory;

public class SearchItemsAssembler {
    private ISearchItemsPresenterFactory presenterFactory;

    public ISearchItemsPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ISearchItemsPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
