package es.developer.achambi.pkmng.modules.search.ability.presenter;

import es.developer.achambi.pkmng.modules.search.ability.screen.ISearchAbilityScreen;

public interface ISearchAbilityPresenterFactory {
    SearchAbilityPresenter buildPresenter(ISearchAbilityScreen screen);
}
