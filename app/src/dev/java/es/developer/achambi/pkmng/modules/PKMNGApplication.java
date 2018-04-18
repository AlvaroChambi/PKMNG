package es.developer.achambi.pkmng.modules;

import android.app.Application;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.search.item.SearchItemsAssembler;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.item.presenter.SearchItemsPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.SearchPokemonAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenterFactory;

public class PKMNGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase database = AppDatabase.buildDatabase(this);
        MainExecutor executor = MainExecutor.buildExecutor();

        OverviewAssembler overviewAssembler = new OverviewAssembler();
        overviewAssembler.setPresenterFactory( new OverviewPresenterFactory(
                new SearchPokemonPresenterFactory(
                        new PokemonDataAccessFactory( database ), executor ), executor
        ) );

        SearchPokemonAssembler pokemonAssembler = new SearchPokemonAssembler();
        pokemonAssembler.setPresenterFactory( new SearchPokemonPresenterFactory(
                new PokemonDataAccessFactory( database ), executor
        ));

        SearchItemsAssembler itemsAssembler = new SearchItemsAssembler();
        itemsAssembler.setPresenterFactory( new SearchItemsPresenterFactory(
                new ItemDataAccessFactory( database ), executor
        ));

        AppWiring.searchPokemonAssembler = pokemonAssembler;
        AppWiring.overviewAssembler = overviewAssembler;
        AppWiring.searchItemsAssembler = itemsAssembler;
    }
}
