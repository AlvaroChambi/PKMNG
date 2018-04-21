package es.developer.achambi.pkmng.modules.search.ability;

import es.developer.achambi.pkmng.modules.search.ability.presenter.ISearchAbilityPresenterFactory;

public class SearchAbilityAssembler {
    private ISearchAbilityPresenterFactory presenterFactory;

    public ISearchAbilityPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ISearchAbilityPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
