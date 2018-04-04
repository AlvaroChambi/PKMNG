package es.developer.achambi.pkmng.modules.search.item.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.view.ISearchItemScreen;
import es.developer.achambi.pkmng.modules.search.item.view.presentation.SearchItemPresentation;

public class SearchItemsPresenter extends ISearchItemsPresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchItemPresentation> {
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";
    private ArrayList<Item> data;
    private ISearchItemScreen view;

    public SearchItemsPresenter( ISearchItemScreen view ) {
        super(view);
        this.view = view;
    }

    @Override
    public void fetchItems(final ResponseHandler<ArrayList<Item>> responseHandler ) {
        ResponseHandler<ArrayList<Item>> handler = new ResponseHandlerDecorator<ArrayList<Item>>(
                responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Item>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };

        MainExecutor.executor().executeRequest(new Request() {
            @Override
            public Response perform() {
                return new Response<>( buildItemsList() );
            }
        }, handler );
    }

    @Override
    public void onItemClicked(SearchItemPresentation itemRepresentation) {
        for( Item item : data ) {
            if( itemRepresentation.id == item.getId() ) {
                view.showItemDetails( item );
            }
        }
    }

    public ArrayList<Item> getItems() {
        return data;
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
