package es.developer.achambi.pkmng.modules.search.nature.presenter;

import es.developer.achambi.pkmng.modules.search.nature.screen.ISearchNatureScreen;

public interface ISearchNaturePresenterFactory {
    SearchNaturePresenter buildPresenter(ISearchNatureScreen screen);
}
