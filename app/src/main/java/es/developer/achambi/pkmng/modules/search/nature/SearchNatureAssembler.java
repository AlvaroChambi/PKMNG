package es.developer.achambi.pkmng.modules.search.nature;

import es.developer.achambi.pkmng.modules.search.nature.presenter.ISearchNaturePresenterFactory;

public class SearchNatureAssembler {
    private ISearchNaturePresenterFactory presenterFactory;

    public ISearchNaturePresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ISearchNaturePresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
