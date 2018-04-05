package es.developer.achambi.pkmng.modules.search.move.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public abstract class ISearchMovePresenter extends Presenter {
    public ISearchMovePresenter(Screen screen) {
        super(screen);
    }

    public abstract void fetchMoves(ResponseHandler<ArrayList<Move>> responseHandler );
}
