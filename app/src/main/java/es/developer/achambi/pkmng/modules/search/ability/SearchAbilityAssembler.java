package es.developer.achambi.pkmng.modules.search.ability;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.AbilityDataAssembler;
import es.developer.achambi.pkmng.modules.search.ability.presenter.ISearchAbilityPresenterFactory;
import es.developer.achambi.pkmng.modules.search.ability.presenter.SearchAbilityPresenterFactory;

public class SearchAbilityAssembler {
    private MainExecutor executor;
    private AbilityDataAssembler abilityDataAssembler;

    public SearchAbilityAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public SearchAbilityAssembler setAbilityDataAssembler(
            AbilityDataAssembler abilityDataAssembler) {
        this.abilityDataAssembler = abilityDataAssembler;
        return this;
    }

    public ISearchAbilityPresenterFactory getPresenterFactory() {
        return new SearchAbilityPresenterFactory(
                abilityDataAssembler.getAbilityDataAccess(),
                executor );
    }
}
