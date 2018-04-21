package es.developer.achambi.pkmng.modules;

import android.content.Context;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.data.StatDataAccessFactory;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.search.item.SearchItemsAssembler;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.item.presenter.SearchItemsPresenterFactory;
import es.developer.achambi.pkmng.modules.search.nature.SearchNatureAssembler;
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.nature.presenter.SearchNaturePresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.SearchPokemonAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenterFactory;

public abstract class BaseAppWiring {
    public static OverviewAssembler overviewAssembler;
    public static SearchPokemonAssembler searchPokemonAssembler;
    public static SearchItemsAssembler searchItemsAssembler;
    public static SearchNatureAssembler searchNatureAssembler;

    public void appWiring( Context context ) {
        AppDatabase database = AppDatabase.buildDatabase(context);
        MainExecutor executor = buildExecutor();

        overviewAssembler = new OverviewAssembler();
        overviewAssembler.setPresenterFactory( new OverviewPresenterFactory(
                new SearchPokemonPresenterFactory(
                        new PokemonDataAccessFactory( database,
                                new StatDataAccessFactory(database) ), executor ), executor
        ) );

        searchPokemonAssembler = new SearchPokemonAssembler();
        searchPokemonAssembler.setPresenterFactory( new SearchPokemonPresenterFactory(
                new PokemonDataAccessFactory(
                        database, new StatDataAccessFactory(database) ), executor
        ));

        searchItemsAssembler = new SearchItemsAssembler();
        searchItemsAssembler.setPresenterFactory( new SearchItemsPresenterFactory(
                new ItemDataAccessFactory( database ), executor
        ));

        searchNatureAssembler = new SearchNatureAssembler();
        searchNatureAssembler.setPresenterFactory( new SearchNaturePresenterFactory(
                new NatureDataAccessFactory(
                        database, new StatDataAccessFactory( database ) ), executor
        ));
    }

    protected MainExecutor buildExecutor() {
        return MainExecutor.buildExecutor();
    }
}
