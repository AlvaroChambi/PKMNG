package es.developer.achambi.pkmng.modules.search.nature.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.search.nature.screen.ISearchNatureScreen;
import es.developer.achambi.pkmng.modules.search.nature.screen.presentation.SearchNaturePresentation;

public class SearchNaturePresenter extends ISearchNaturePresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchNaturePresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private ISearchNatureScreen searchNatureView;
    private ArrayList<Nature> data;

    public SearchNaturePresenter( ISearchNatureScreen searchNatureView ) {
        this.searchNatureView = searchNatureView;
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
                return new Response( buildNatureList() );
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

    private ArrayList<Nature> buildNatureList() {
        ArrayList<Nature> natureList = new ArrayList<>();
        for( int i = 0; i < 25 ; i++ ) {
            Nature nature = new Nature();
            nature.setId(i);
            nature.setName("modest");
            nature.setIncreasedStat(Stat.ATTACK);
            nature.setDecreasedStat(Stat.DEFENSE);
            natureList.add( nature );
        }
        return natureList;
    }
}
