package es.developer.achambi.pkmng.modules.search.move.screen;

import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public interface ISearchMoveScreen extends Screen{
    void returnSelectedMove( Move move );
}
