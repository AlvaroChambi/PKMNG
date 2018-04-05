package es.developer.achambi.pkmng.modules.search.nature.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public abstract class ISearchNaturePresenter extends Presenter {
    public abstract void fetchNatureList( ResponseHandler<ArrayList<Nature>> responseHandler );
}
