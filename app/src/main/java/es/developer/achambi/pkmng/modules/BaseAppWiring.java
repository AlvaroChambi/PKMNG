package es.developer.achambi.pkmng.modules;

import android.content.Context;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.calculator.DamageCalculatorAssembler;
import es.developer.achambi.pkmng.modules.calculator.presenter.DamageCalculatorPresenterFactory;
import es.developer.achambi.pkmng.modules.create.CreateConfigurationAssembler;
import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationPresenterFactory;
import es.developer.achambi.pkmng.modules.data.stat.StatDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccessFactory;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.search.ability.SearchAbilityAssembler;
import es.developer.achambi.pkmng.modules.search.ability.data.AbilityDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.configuration.SearchConfigurationAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.ConfigurationDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.item.SearchItemsAssembler;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.move.SearchMoveAssembler;
import es.developer.achambi.pkmng.modules.search.move.data.MoveDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.nature.SearchNatureAssembler;
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.SearchPokemonAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;

public abstract class BaseAppWiring {
    public static OverviewAssembler overviewAssembler;
    public static SearchPokemonAssembler searchPokemonAssembler;
    public static SearchItemsAssembler searchItemsAssembler;
    public static SearchNatureAssembler searchNatureAssembler;
    public static SearchAbilityAssembler searchAbilityAssembler;
    public static SearchMoveAssembler searchMoveAssembler;
    public static SearchConfigurationAssembler searchConfigurationAssembler;
    public static CreateConfigurationAssembler createConfigurationAssembler;
    public static DamageCalculatorAssembler damageCalculatorAssembler;
    public static ConfigurationDataAssembler configurationDataAssembler;
    public static PokemonDataAssembler pokemonDataAssembler;

    public void appWiring( Context context ) {
        AppDatabase database = AppDatabase.buildDatabase(context);
        MainExecutor executor = buildExecutor();
        pokemonDataAssembler = new PokemonDataAssembler();
        pokemonDataAssembler.setAppDatabase(database)
                .setPokemonDataAccessFactory(new PokemonDataAccessFactory())
                .setStatDataAccessFactory(new StatDataAccessFactory())
                .setTypeDataAccessFactory(new TypeDataAccessFactory());

        configurationDataAssembler = new ConfigurationDataAssembler();
        configurationDataAssembler.setDatabase(database)
                .setConfigurationDataAccessFactory(new ConfigurationDataAccessFactory())
                .setPokemonDataAssembler(pokemonDataAssembler)
                .setTypeDataAccessFactory(new TypeDataAccessFactory())
                .setMoveDataAccessFactory(new MoveDataAccessFactory())
                .setItemDataAccessFactory(new ItemDataAccessFactory())
                .setAbilityDataAccessFactory(new AbilityDataAccessFactory())
                .setNatureDataAccessFactory(new NatureDataAccessFactory())
                .setStatDataAccessFactory(new StatDataAccessFactory());

        searchPokemonAssembler = new SearchPokemonAssembler();
        searchPokemonAssembler.setMainExecutor(executor)
                .setPokemonDataAssembler( pokemonDataAssembler );

        searchConfigurationAssembler = new SearchConfigurationAssembler();
        searchConfigurationAssembler.setMainExecutor(executor)
                .setConfigurationDataAssembler(configurationDataAssembler);

        overviewAssembler = new OverviewAssembler();
        overviewAssembler.setSearchConfigurationAssembler( searchConfigurationAssembler )
                .setSearchPokemonAssembler( searchPokemonAssembler )
                .setExecutor( executor );

        createConfigurationAssembler = new CreateConfigurationAssembler();


        damageCalculatorAssembler = new DamageCalculatorAssembler();
    }

    protected MainExecutor buildExecutor() {
        return MainExecutor.buildExecutor();
    }
}
