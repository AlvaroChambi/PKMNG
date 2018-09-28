package es.developer.achambi.pkmng.modules.search.ability.presenter;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccess;
import es.developer.achambi.pkmng.modules.search.ability.screen.ISearchAbilityScreen;

public class SearchAbilityPresenterFactory implements ISearchAbilityPresenterFactory {
    private IAbilityDataAccess dataAccess;
    private MainExecutor executor;

    public SearchAbilityPresenterFactory(IAbilityDataAccess dataAccess,
                                         MainExecutor executor) {
        this.dataAccess = dataAccess;
        this.executor = executor;
    }

    @Override
    public SearchAbilityPresenter buildPresenter(ISearchAbilityScreen screen) {
        return new SearchAbilityPresenter( screen, dataAccess, executor );
    }
}
