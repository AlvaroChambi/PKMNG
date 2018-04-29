package es.developer.achambi.pkmng.modules.search.nature.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.nature.screen.ISearchNatureScreen;

public class SearchNaturePresenterFactory implements ISearchNaturePresenterFactory {
    private INatureDataAccessFactory dataAccessFactory;
    private MainExecutor executor;

    public SearchNaturePresenterFactory(INatureDataAccessFactory dataAccessFactory,
                                        MainExecutor executor) {
        this.dataAccessFactory = dataAccessFactory;
        this.executor = executor;
    }

    @Override
    public SearchNaturePresenter buildPresenter(ISearchNatureScreen screen) {
        return new SearchNaturePresenter( screen, dataAccessFactory.buildDataAccess(), executor  );
    }
}
