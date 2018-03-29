package es.developer.achambi.pkmng.modules.search.nature.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public interface ISearchNaturePresenter extends ViewPresenter {
    void fetchNatureList( ResponseHandler<ArrayList<Nature>> responseHandler );
}
