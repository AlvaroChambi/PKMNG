package es.developer.achambi.pkmng.modules.search.item.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.screen.ISearchItemScreen;
import es.developer.achambi.pkmng.modules.search.item.screen.presentation.SearchItemPresentation;

public class SearchItemsPresenter extends ISearchItemsPresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchItemPresentation> {
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";
    private ArrayList<Item> data;
    private ISearchItemScreen screen;
    private IItemDataAccess dataAccess;

    public SearchItemsPresenter( ISearchItemScreen screen,
                                 IItemDataAccess dataAccess,
                                 MainExecutor executor ) {
        super(screen, executor);
        this.screen = screen;
        data = new ArrayList<>();
        this.dataAccess = dataAccess;
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

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessData() );
            }
        }, handler );
    }

    @Override
    public void fetchItemsQuery(final String query,
                                final ResponseHandler<ArrayList<Item>> responseHandler) {
        ResponseHandler<ArrayList<Item>> handler = new ResponseHandlerDecorator<ArrayList<Item>>(
                responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Item>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.queryItemData(query) );
            }
        }, handler );
    }

    @Override
    public void onItemClicked(SearchItemPresentation itemRepresentation) {
        for( Item item : data ) {
            if( itemRepresentation.id == item.getId() ) {
                screen.showItemDetails( item );
            }
        }
    }

    public ArrayList<Item> getItems() {
        return data;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList( DATA_SAVED_STATE, data );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        data = bundle.getParcelableArrayList( DATA_SAVED_STATE );
    }
}
