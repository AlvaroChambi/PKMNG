package es.developer.achambi.pkmng.modules.search.nature.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.search.nature.screen.ISearchNatureScreen;
import es.developer.achambi.pkmng.modules.search.nature.screen.presentation.SearchNaturePresentation;

public class SearchNaturePresenter extends ISearchNaturePresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchNaturePresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private ISearchNatureScreen searchNatureView;
    private ArrayList<Nature> data;
    private INatureDataAccess dataAccess;

    public SearchNaturePresenter(ISearchNatureScreen searchNatureScreen,
                                 INatureDataAccess dataAccess,
                                 MainExecutor executor) {
        super(searchNatureScreen, executor);
        this.searchNatureView = searchNatureScreen;
        this.dataAccess = dataAccess;
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

    @Override
    public void fetchNatureList(final ResponseHandler<ArrayList<Nature>> responseHandler ) {
        ResponseHandler<ArrayList<Nature>> handler = new ResponseHandlerDecorator<ArrayList<Nature>>(
                responseHandler) {
            @Override
            public void onSuccess(Response<ArrayList<Nature>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessData() );
            }
        }, handler);
    }

    @Override
    public void fetchNatureQueryList(final String query,
                                     final ResponseHandler<ArrayList<Nature>> responseHandler) {
        ResponseHandler<ArrayList<Nature>> handler = new ResponseHandlerDecorator<ArrayList<Nature>>(
                responseHandler) {
            @Override
            public void onSuccess(Response<ArrayList<Nature>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessNatureQueryData(query) );
            }
        }, handler);
    }

    @Override
    public void onItemClicked(SearchNaturePresentation item) {
        for( Nature nature : data ) {
            if( item.id == nature.getId() ) {
                searchNatureView.returnSelectedNature( nature );
            }
        }
    }

    public ArrayList<Nature> getNatureList() {
        return data;
    }
}
