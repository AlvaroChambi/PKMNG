package es.developer.achambi.pkmng.modules.search.item.screen;

import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public interface ISearchItemScreen extends Screen {
    void showItemDetails( Item item );
}