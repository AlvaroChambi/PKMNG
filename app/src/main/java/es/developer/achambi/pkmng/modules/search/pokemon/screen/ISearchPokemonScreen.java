package es.developer.achambi.pkmng.modules.search.pokemon.screen;

import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public interface ISearchPokemonScreen extends Screen {
    void showPokemonDetails( Pokemon item );
}
