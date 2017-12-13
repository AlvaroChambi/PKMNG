package es.developer.achambi.pkmng.modules.overview.presenter;

import java.util.List;

import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.view.adapter.OverviewListAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

public interface IOverviewPresenter extends ViewPresenter, OverviewListAdapter.OnItemClickedListener{
    void onQueryTextSubmit(String query);
    void onQueryTextChanged(String query);
    List<BasePokemon> fetchPokemonList();
}
