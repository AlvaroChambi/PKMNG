package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.List;

import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;

public interface IOverviewPresenter extends ViewPresenter{
    void onQueryTextSubmit(String query);
    void onQueryTextChanged(String query);
    List<BasePokemon> fetchPokemonList();
}
