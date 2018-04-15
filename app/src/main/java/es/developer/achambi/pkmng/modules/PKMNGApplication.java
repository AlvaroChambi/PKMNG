package es.developer.achambi.pkmng.modules;

import android.app.Application;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.SearchPokemonAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;

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

        AppWiring.searchPokemonAssembler = pokemonAssembler;
        AppWiring.overviewAssembler = overviewAssembler;
        AppWiring.executor = executor;
    }
}
