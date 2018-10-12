package es.developer.achambi.pkmng.modules.search.nature.screen;

import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public interface ISearchNatureScreen extends Screen {
    void returnSelectedNature(Nature nature );
}
