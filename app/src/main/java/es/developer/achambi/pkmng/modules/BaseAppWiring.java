package es.developer.achambi.pkmng.modules;

import android.content.Context;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.data.StatDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.TypeDataAccessFactory;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.search.ability.SearchAbilityAssembler;
import es.developer.achambi.pkmng.modules.search.ability.data.AbilityDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.ability.presenter.SearchAbilityPresenterFactory;
import es.developer.achambi.pkmng.modules.search.configuration.SearchConfigurationAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.ConfigurationDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenterFactory;
import es.developer.achambi.pkmng.modules.search.item.SearchItemsAssembler;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.item.presenter.SearchItemsPresenterFactory;
import es.developer.achambi.pkmng.modules.search.move.SearchMoveAssembler;
import es.developer.achambi.pkmng.modules.search.move.data.MoveDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.move.presenter.SearchMovePresenterFactory;
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
    public static SearchAbilityAssembler searchAbilityAssembler;
    public static SearchMoveAssembler searchMoveAssembler;
    public static SearchConfigurationAssembler searchConfigurationAssembler;

    public void appWiring( Context context ) {
        AppDatabase database = AppDatabase.buildDatabase(context);
        MainExecutor executor = buildExecutor();
        StatDataAccessFactory statDataAccessFactory = new StatDataAccessFactory(database);
        TypeDataAccessFactory typeDataAccessFactory = new TypeDataAccessFactory(database);
        PokemonDataAccessFactory pokemonDataAccessFactory = new PokemonDataAccessFactory(database,
                statDataAccessFactory, typeDataAccessFactory);
        ItemDataAccessFactory itemDataAccessFactory = new ItemDataAccessFactory(database);
        NatureDataAccessFactory natureDataAccessFactory = new NatureDataAccessFactory(database,
                statDataAccessFactory );
        AbilityDataAccessFactory abilityDataAccessFactory = new AbilityDataAccessFactory(database);
        MoveDataAccessFactory moveDataAccessFactory = new MoveDataAccessFactory(database,
                typeDataAccessFactory );
        ConfigurationDataAccessFactory configDataAccessFactory = new ConfigurationDataAccessFactory(
                database,
                pokemonDataAccessFactory,
                statDataAccessFactory,
                moveDataAccessFactory,
                itemDataAccessFactory,
                natureDataAccessFactory,
                abilityDataAccessFactory );

        SearchPokemonPresenterFactory pokemonPresenterFactory = new SearchPokemonPresenterFactory(
                pokemonDataAccessFactory, executor
        );
        SearchConfigurationPresenterFactory configurationPresenterFactory =
                new SearchConfigurationPresenterFactory( configDataAccessFactory, executor );

        overviewAssembler = new OverviewAssembler();
        overviewAssembler.setPresenterFactory( new OverviewPresenterFactory(
                pokemonPresenterFactory, configurationPresenterFactory, executor ) );

        searchPokemonAssembler = new SearchPokemonAssembler();
        searchPokemonAssembler.setPresenterFactory( pokemonPresenterFactory );

        searchItemsAssembler = new SearchItemsAssembler();
        searchItemsAssembler.setPresenterFactory( new SearchItemsPresenterFactory(
                itemDataAccessFactory, executor
        ));

        searchNatureAssembler = new SearchNatureAssembler();
        searchNatureAssembler.setPresenterFactory( new SearchNaturePresenterFactory(
                natureDataAccessFactory, executor
        ));

        searchAbilityAssembler = new SearchAbilityAssembler();
        searchAbilityAssembler.setPresenterFactory( new SearchAbilityPresenterFactory(
                abilityDataAccessFactory, executor
        ));

        searchMoveAssembler = new SearchMoveAssembler();
        searchMoveAssembler.setPresenterFactory( new SearchMovePresenterFactory(
                moveDataAccessFactory, executor
        ));
    }

    protected MainExecutor buildExecutor() {
        return MainExecutor.buildExecutor();
    }
}
