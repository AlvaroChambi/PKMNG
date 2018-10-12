package es.developer.achambi.pkmng.modules.search.nature.presenter;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.screen.ISearchNatureScreen;

public class SearchNaturePresenterFactory implements ISearchNaturePresenterFactory {
    private INatureDataAccess dataAccess;
    private MainExecutor executor;

    public SearchNaturePresenterFactory(INatureDataAccess dataAccess,
                                        MainExecutor executor) {
        this.dataAccess = dataAccess;
        this.executor = executor;
    }

    @Override
    public SearchNaturePresenter buildPresenter(ISearchNatureScreen screen) {
        return new SearchNaturePresenter( screen, dataAccess, executor  );
    }
}
