package es.developer.achambi.pkmng.modules;

import es.developer.achambi.coreframework.db.dao.ConfigurationDAO;
import es.developer.achambi.pkmng.modules.search.AbilityDataAssembler;
import es.developer.achambi.pkmng.modules.search.ItemDataAssembler;
import es.developer.achambi.pkmng.modules.search.NatureDataAssembler;
import es.developer.achambi.pkmng.modules.search.StatDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.ConfigurationDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;

public class ConfigurationDataAssembler {
    private ConfigurationDAO configurationDAO;
    private ConfigurationDataAccessFactory configurationDataAccessFactory;
    private PokemonDataAssembler pokemonDataAssembler;
    private MoveDataAssembler moveDataAssembler;
    private ItemDataAssembler itemDataAssembler;
    private AbilityDataAssembler abilityDataAssembler;
    private NatureDataAssembler natureDataAssembler;
    private StatDataAssembler statDataAssembler;

    public ConfigurationDataAssembler setMoveDataAssembler(MoveDataAssembler moveDataAssembler) {
        this.moveDataAssembler = moveDataAssembler;
        return this;
    }

    public ConfigurationDataAssembler setConfigurationDataAccessFactory(
            ConfigurationDataAccessFactory configurationDataAccessFactory) {
        this.configurationDataAccessFactory = configurationDataAccessFactory;
        return this;
    }

    public ConfigurationDataAssembler setPokemonDataAssembler(
            PokemonDataAssembler pokemonDataAssembler) {
        this.pokemonDataAssembler = pokemonDataAssembler;
        return this;
    }

    public ConfigurationDataAssembler setConfigurationDAO(ConfigurationDAO configurationDAO) {
        this.configurationDAO = configurationDAO;
        return this;
    }

    public ConfigurationDataAssembler setItemDataAssembler(ItemDataAssembler itemDataAssembler) {
        this.itemDataAssembler = itemDataAssembler;
        return this;
    }

    public ConfigurationDataAssembler setAbilityDataAssembler(
            AbilityDataAssembler abilityDataAssembler) {
        this.abilityDataAssembler = abilityDataAssembler;
        return this;
    }

    public ConfigurationDataAssembler setNatureDataAssembler(
            NatureDataAssembler natureDataAssembler) {
        this.natureDataAssembler = natureDataAssembler;
        return this;
    }

    public ConfigurationDataAssembler setStatDataAssembler(StatDataAssembler statDataAssembler) {
        this.statDataAssembler = statDataAssembler;
        return this;
    }

    public IConfigurationDataAccess getConfigurationDataAccess() {
        return configurationDataAccessFactory.buildDataAccess(
                configurationDAO,
                pokemonDataAssembler.getPokemonDataAccess(),
                moveDataAssembler.getMoveDataAccess(),
                itemDataAssembler.getDataAccess(),
                abilityDataAssembler.getAbilityDataAccess(),
                natureDataAssembler.getNatureDataAccess(),
                statDataAssembler.getStatDataAccess() );
    }
}
