package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import es.developer.achambi.pkmng.modules.search.pokemon.screen.ISearchPokemonScreen;

public interface ISearchPokemonPresenterFactory {
    SearchPokemonPresenter buildPresenter( ISearchPokemonScreen screen );
}
