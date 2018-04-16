package es.developer.achambi.pkmng.modules.search.pokemon;

import es.developer.achambi.pkmng.modules.search.pokemon.presenter.ISearchPokemonPresenterFactory;

public class SearchPokemonAssembler {
    private ISearchPokemonPresenterFactory presenterFactory;

    public ISearchPokemonPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(ISearchPokemonPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
