package es.developer.achambi.pkmng.modules.search.move.presenter;

import java.util.ArrayList;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.coreframework.threading.ResponseHandler;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public abstract class ISearchMovePresenter extends Presenter {
    public ISearchMovePresenter(Screen screen, MainExecutor executor) {
        super(screen, executor);
    }

    public abstract void fetchMoves(int pokemonId,
                                    ResponseHandler<ArrayList<Move>> responseHandler );
    public abstract void fetchMovesQuery( int pokemonId,
                                          String query,
                                          ResponseHandler<ArrayList<Move>> responseHandler );
}
