package es.developer.achambi.pkmng.modules.search.nature;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.search.NatureDataAssembler;
import es.developer.achambi.pkmng.modules.search.nature.presenter.ISearchNaturePresenterFactory;
import es.developer.achambi.pkmng.modules.search.nature.presenter.SearchNaturePresenterFactory;

public class SearchNatureAssembler {
    private MainExecutor executor;
    private NatureDataAssembler natureDataAssembler;

    public SearchNatureAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public SearchNatureAssembler setNatureDataAssembler(NatureDataAssembler natureDataAssembler) {
        this.natureDataAssembler = natureDataAssembler;
        return this;
    }

    public ISearchNaturePresenterFactory getPresenterFactory() {
        return new SearchNaturePresenterFactory( natureDataAssembler.getNatureDataAccess(),
                executor );
    }
}
