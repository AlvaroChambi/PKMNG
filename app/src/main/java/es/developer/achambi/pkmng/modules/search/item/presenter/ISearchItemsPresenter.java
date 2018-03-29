package es.developer.achambi.pkmng.modules.search.item.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public interface ISearchItemsPresenter extends ViewPresenter {
    void fetchItems( ResponseHandler<ArrayList<Item>> responseHandler );
}
