package es.developer.achambi.pkmng.modules;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.stat.StatDataAccessFactory;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.ability.data.AbilityDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.configuration.data.ConfigurationDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.move.data.MoveDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccessFactory;

public class ConfigurationDataAssembler {
    private AppDatabase database;
    private ConfigurationDataAccessFactory configurationDataAccessFactory;
    private TypeDataAccessFactory typeDataAccessFactory;
    private PokemonDataAssembler pokemonDataAssembler;
    private MoveDataAccessFactory moveDataAccessFactory;
    private ItemDataAccessFactory itemDataAccessFactory;
    private AbilityDataAccessFactory abilityDataAccessFactory;
    private NatureDataAccessFactory natureDataAccessFactory;
    private StatDataAccessFactory statDataAccessFactory;

    public ConfigurationDataAssembler setDatabase(AppDatabase database) {
        this.database = database;
        return this;
    }

    public ConfigurationDataAssembler setConfigurationDataAccessFactory(
            ConfigurationDataAccessFactory configurationDataAccessFactory) {
        this.configurationDataAccessFactory = configurationDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setTypeDataAccessFactory(
            TypeDataAccessFactory typeDataAccessFactory) {
        this.typeDataAccessFactory = typeDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setPokemonDataAssembler(
            PokemonDataAssembler pokemonDataAssembler) {
        this.pokemonDataAssembler = pokemonDataAssembler;
        return this;
    }

    public ConfigurationDataAssembler setMoveDataAccessFactory(
            MoveDataAccessFactory moveDataAccessFactory) {
        this.moveDataAccessFactory = moveDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setItemDataAccessFactory(
            ItemDataAccessFactory itemDataAccessFactory) {
        this.itemDataAccessFactory = itemDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setAbilityDataAccessFactory(
            AbilityDataAccessFactory abilityDataAccessFactory) {
        this.abilityDataAccessFactory = abilityDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setNatureDataAccessFactory(
            NatureDataAccessFactory natureDataAccessFactory) {
        this.natureDataAccessFactory = natureDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setStatDataAccessFactory(
            StatDataAccessFactory statDataAccessFactory) {
        this.statDataAccessFactory = statDataAccessFactory;
        return this;
    }

    public IConfigurationDataAccess getConfigurationDataAccess() {
        return configurationDataAccessFactory.buildDataAccess(
                database.configurationsModel(),
                pokemonDataAssembler.getPokemonDataAccess(),
                moveDataAccessFactory.buildDataAccess( database.movesModel(),
                        typeDataAccessFactory.buildDataAccess( database.typeModel() ) ),
                itemDataAccessFactory.buildDataAccess( database.itemsModel() ),
                abilityDataAccessFactory.buildDataAccess( database.abilitiesModel() ),
                natureDataAccessFactory.buildDataAccess( database.naturesModel(),
                        statDataAccessFactory.buildDataAccess( database.statsModel() ) ),
                statDataAccessFactory.buildDataAccess( database.statsModel() ) );
    }
}
