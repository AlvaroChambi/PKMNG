package es.developer.achambi.pkmng.modules.search.item.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public abstract class ISearchItemsPresenter extends Presenter {
    public ISearchItemsPresenter(Screen screen) {
        super(screen);
    }

    public abstract void fetchItems(ResponseHandler<ArrayList<Item>> responseHandler );
}
