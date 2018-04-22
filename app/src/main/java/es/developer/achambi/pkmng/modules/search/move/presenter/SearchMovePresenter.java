package es.developer.achambi.pkmng.modules.search.move.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.move.data.MoveDataAccess;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.screen.ISearchMoveScreen;
import es.developer.achambi.pkmng.modules.search.move.screen.presentation.SearchMovePresentation;

public class SearchMovePresenter extends ISearchMovePresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchMovePresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private ArrayList<Move> data;
    private ISearchMoveScreen screen;
    private MoveDataAccess dataAccess;

    public SearchMovePresenter( ISearchMoveScreen screen,
                                MoveDataAccess dataAccess,
                                MainExecutor executor ) {
        super(screen, executor);
        this.screen = screen;
        this.dataAccess = dataAccess;
    }

    @Override
    public void fetchMoves(final int pokemonId,
                           final ResponseHandler<ArrayList<Move>> responseHandler ) {
        ResponseHandler<ArrayList<Move>> handler = new ResponseHandlerDecorator<ArrayList<Move>>(
                responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Move>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };
        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessPokemonMovesData(pokemonId) );
            }
        }, handler );
    }

    public ArrayList<Move> getMoves() {
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

    @Override
    public void onItemClicked(SearchMovePresentation item) {
        for( Move move : data ) {
            if( item.id == move.getId() ) {
                screen.returnSelectedMove( move );
            }
        }
    }
}
