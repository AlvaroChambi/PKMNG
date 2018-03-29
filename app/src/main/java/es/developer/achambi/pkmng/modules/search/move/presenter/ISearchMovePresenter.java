package es.developer.achambi.pkmng.modules.search.move.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public interface ISearchMovePresenter extends ViewPresenter{
    void fetchMoves( ResponseHandler<ArrayList<Move>> responseHandler );
}
