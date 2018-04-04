package es.developer.achambi.pkmng.modules.search.pokemon.view;

import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public interface ISearchPokemonScreen extends Screen {
    void showPokemonDetails( Pokemon item );
}
