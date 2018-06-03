package es.developer.achambi.pkmng.modules;

import android.content.Context;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.calculator.DamageCalculatorAssembler;
import es.developer.achambi.pkmng.modules.create.CreateConfigurationAssembler;
import es.developer.achambi.pkmng.modules.data.stat.StatDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.search.AbilityDataAssembler;
import es.developer.achambi.pkmng.modules.search.ItemDataAssembler;
import es.developer.achambi.pkmng.modules.search.NatureDataAssembler;
import es.developer.achambi.pkmng.modules.search.StatDataAssembler;
import es.developer.achambi.pkmng.modules.search.TypeDataAssembler;
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

    public void appWiring( Context context ) {
        AppDatabase database = AppDatabase.buildDatabase(context);
        MainExecutor executor = buildExecutor();
        AbilityDataAssembler abilityDataAssembler = new AbilityDataAssembler();
        abilityDataAssembler.setAbilitiesDAO( database.abilitiesModel() )
                .setDataAccessFactory(new AbilityDataAccessFactory())
                .setFormatter(new DataFormatUtil());
        ItemDataAssembler itemDataAssembler = new ItemDataAssembler();
        itemDataAssembler.setItemDAO( database.itemsModel() )
                .setDataAccessFactory(new ItemDataAccessFactory())
                .setFormatter(new DataFormatUtil());
        StatDataAssembler statDataAssembler = new StatDataAssembler();
        statDataAssembler.setStatsDAO( database.statsModel() )
                .setDataAccessFactory(new StatDataAccessFactory());
        TypeDataAssembler typeDataAssembler = new TypeDataAssembler();
        typeDataAssembler.setTypeDAO( database.typeModel() )
                .setDataAccessFactory(new TypeDataAccessFactory());

        MoveDataAssembler moveDataAssembler = new MoveDataAssembler();
        moveDataAssembler.setMovesDAO(database.movesModel())
                .setMoveDataAccessFactory(new MoveDataAccessFactory())
                .setTypeDataAssembler(typeDataAssembler)
                .setFormatter(new DataFormatUtil());
        NatureDataAssembler natureDataAssembler = new NatureDataAssembler()
                .setNaturesDAO(database.naturesModel())
                .setStatDataAssembler(statDataAssembler)
                .setNatureDataAccessFactory(new NatureDataAccessFactory());
        PokemonDataAssembler pokemonDataAssembler = new PokemonDataAssembler();
        pokemonDataAssembler.setPokemonDAO(database.pokemonModel())
                .setPokemonDataAccessFactory(new PokemonDataAccessFactory())
                .setStatDataAssembler(statDataAssembler)
                .setTypeDataAssembler(typeDataAssembler)
                .setImageResourceBuilder( new ImageResourceBuilder() );
        ConfigurationDataAssembler configurationDataAssembler = new ConfigurationDataAssembler();
        configurationDataAssembler.setConfigurationDAO(database.configurationsModel())
                .setConfigurationDataAccessFactory(new ConfigurationDataAccessFactory())
                .setPokemonDataAssembler(pokemonDataAssembler)
                .setMoveDataAssembler(moveDataAssembler)
                .setItemDataAssembler(itemDataAssembler)
                .setAbilityDataAssembler(abilityDataAssembler)
                .setNatureDataAssembler(natureDataAssembler)
                .setStatDataAssembler(statDataAssembler);


        searchPokemonAssembler = new SearchPokemonAssembler();
        searchPokemonAssembler.setMainExecutor(executor)
                .setPokemonDataAssembler(pokemonDataAssembler);
        searchConfigurationAssembler = new SearchConfigurationAssembler();
        searchConfigurationAssembler.setMainExecutor(executor)
                .setConfigurationDataAssembler(configurationDataAssembler);
        overviewAssembler = new OverviewAssembler();
        overviewAssembler.setSearchConfigurationAssembler( searchConfigurationAssembler )
                .setSearchPokemonAssembler( searchPokemonAssembler )
                .setExecutor( executor );
        createConfigurationAssembler = new CreateConfigurationAssembler();
        createConfigurationAssembler.setConfigurationDataAssembler(configurationDataAssembler)
                .setExecutor( executor );
        damageCalculatorAssembler = new DamageCalculatorAssembler();
        damageCalculatorAssembler.setDataAssembler(configurationDataAssembler)
                .setExecutor( executor );
        searchMoveAssembler = new SearchMoveAssembler();
        searchMoveAssembler.setExecutor( executor )
                .setMoveDataAssembler(moveDataAssembler);
        searchItemsAssembler = new SearchItemsAssembler();
        searchItemsAssembler.setExecutor(executor)
                .setItemDataAssembler(itemDataAssembler);
        searchAbilityAssembler = new SearchAbilityAssembler()
                .setExecutor(executor)
                .setAbilityDataAssembler(abilityDataAssembler);
        searchNatureAssembler = new SearchNatureAssembler()
                .setExecutor(executor)
                .setNatureDataAssembler(natureDataAssembler);

    }

    protected MainExecutor buildExecutor() {
        return MainExecutor.buildExecutor();
    }
}
