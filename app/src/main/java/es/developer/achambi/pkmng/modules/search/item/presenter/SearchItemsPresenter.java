package es.developer.achambi.pkmng.modules.search.item.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.view.ISearchItemView;
import es.developer.achambi.pkmng.modules.search.item.view.representation.SearchItemPresentation;

public class SearchItemsPresenter implements ISearchItemsPresenter,
        SearchAdapterDecorator.OnItemClickedListener<SearchItemPresentation> {
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";
    private ArrayList<Item> data;
    private ISearchItemView view;

    public SearchItemsPresenter( ISearchItemView view ) {
        this.view = view;
    }

    @Override
    public ArrayList<Item> fetchItems() {
        if( data == null ) {
            data = buildItemsList();
        }
        return data;
    }

    @Override
    public void onItemClicked(SearchItemPresentation itemRepresentation) {
        for( Item item : data ) {
            if( itemRepresentation.id == item.getId() ) {
                view.showItemDetails( item );
            }
        }
    }

    private ArrayList<Item> buildItemsList() {
        ArrayList<Item> items = new ArrayList<>();
        for( int i = 0; i < 959; i++ ) {
            Item item = new Item();
            item.setId(i);
            item.setDescriptionShort("Holder has 1.5× Defense and Special Defense, as long as it\'s not fully evolved.");
            item.setDescription("Held by a Pokémon that is not fully evolved\n:   Holder has 1.5× Defense and Special Defense.");
            item.setName("eviolite");
            items.add(item);
        }

        return items;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList( DATA_SAVED_STATE, data );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        data = bundle.getParcelableArrayList( DATA_SAVED_STATE );
    }
}
