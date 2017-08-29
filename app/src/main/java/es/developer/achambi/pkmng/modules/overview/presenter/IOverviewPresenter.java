package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.List;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public interface IOverviewPresenter {
    void onQueryTextSubmit(String query);
    void onQueryTextChanged(String query);
    List<Pokemon> getPokemonList();
}
