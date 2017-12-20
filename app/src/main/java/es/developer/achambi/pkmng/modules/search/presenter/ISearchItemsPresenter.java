package es.developer.achambi.pkmng.modules.search.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.model.Item;

public interface ISearchItemsPresenter extends ViewPresenter {
    ArrayList<Item> fetchItems();
}
