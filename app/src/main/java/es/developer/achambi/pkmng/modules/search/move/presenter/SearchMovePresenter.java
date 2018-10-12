package es.developer.achambi.pkmng.modules.search.move.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.coreframework.threading.Error;
import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.coreframework.threading.Request;
import es.developer.achambi.coreframework.threading.Response;
import es.developer.achambi.coreframework.threading.ResponseHandler;
import es.developer.achambi.coreframework.threading.ResponseHandlerDecorator;
import es.developer.achambi.coreframework.ui.DataState;
import es.developer.achambi.coreframework.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccess;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.screen.ISearchMoveScreen;
import es.developer.achambi.pkmng.modules.search.move.screen.presentation.SearchMovePresentation;

public class SearchMovePresenter extends ISearchMovePresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchMovePresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private ArrayList<Move> data;
    private ISearchMoveScreen screen;
    private IMoveDataAccess dataAccess;

    public SearchMovePresenter( ISearchMoveScreen screen,
                                IMoveDataAccess dataAccess,
                                MainExecutor executor ) {
        super(screen, executor);
        this.screen = screen;
        this.dataAccess = dataAccess;
    }

    @Override
    public void fetchMoves(final int pokemonId,
                           final ResponseHandler<ArrayList<Move>> responseHandler ) {
        setDataState(DataState.NOT_FINISHED);
        ResponseHandler<ArrayList<Move>> handler = new ResponseHandlerDecorator<ArrayList<Move>>(
                responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Move>> response) {
                setDataState( DataState.SUCCESS );
                data = response.getData();
                responseHandler.onSuccess( response );
            }

            @Override
            public void onError(Error error) {
                setDataState( DataState.ERROR );
                super.onError(error);
            }
        };
        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessPokemonMovesData(pokemonId) );
            }
        }, handler );
    }

    @Override
    public void fetchMovesQuery( final int pokemonId, final String query,
                                 final ResponseHandler<ArrayList<Move>> responseHandler) {
        setDataState(DataState.NOT_FINISHED);
        ResponseHandler<ArrayList<Move>> handler = new ResponseHandlerDecorator<ArrayList<Move>>(
                responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Move>> response) {
                setDataState( DataState.SUCCESS );
                data = response.getData();
                responseHandler.onSuccess( response );
            }

            @Override
            public void onError(Error error) {
                setDataState( DataState.ERROR );
                super.onError(error);
            }
        };
        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.queryPokemonMovesData(pokemonId, query) );
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
