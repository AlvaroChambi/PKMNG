package es.developer.achambi.pkmng.modules;

import android.content.Context;

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

public abstract class BaseAppWiring {
    public static OverviewAssembler overviewAssembler;
    public static SearchPokemonAssembler searchPokemonAssembler;
    public static SearchItemsAssembler searchItemsAssembler;

    public void appWiring( Context context ) {
        AppDatabase database = AppDatabase.buildDatabase(context);
        MainExecutor executor = buildExecutor();

        OverviewAssembler overviewScreenAssembler = new OverviewAssembler();
        overviewScreenAssembler.setPresenterFactory( new OverviewPresenterFactory(
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

        searchPokemonAssembler = pokemonAssembler;
        overviewAssembler = overviewScreenAssembler;
        searchItemsAssembler = itemsAssembler;
    }

    protected MainExecutor buildExecutor() {
        return MainExecutor.buildExecutor();
    }
}
