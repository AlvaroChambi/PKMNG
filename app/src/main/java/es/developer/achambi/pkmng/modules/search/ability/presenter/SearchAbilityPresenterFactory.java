package es.developer.achambi.pkmng.modules.search.ability.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.ability.screen.ISearchAbilityScreen;

public class SearchAbilityPresenterFactory implements ISearchAbilityPresenterFactory {
    private IAbilityDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public SearchAbilityPresenterFactory(IAbilityDataAccessFactory dataAccessFactory,
                                         MainExecutor executor) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    @Override
    public SearchAbilityPresenter buildPresenter(ISearchAbilityScreen screen) {
        return new SearchAbilityPresenter( screen, dataAccessFactory.buildDataAccess(), executor );
    }
}
