package es.developer.achambi.pkmng.modules.search.item.presenter;

import java.util.ArrayList;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.coreframework.threading.ResponseHandler;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public abstract class ISearchItemsPresenter extends Presenter {
    public ISearchItemsPresenter(Screen screen, MainExecutor executor) {
        super(screen, executor);
    }

    public abstract void fetchItems(ResponseHandler<ArrayList<Item>> responseHandler );
    public abstract void fetchItemsQuery( String query,
                                          ResponseHandler<ArrayList<Item>> responseHandler );
}
