package es.developer.achambi.pkmng.modules.search.configuration.data;

import es.developer.achambi.pkmng.database.dao.ConfigurationDAO;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccess;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess;

public class ConfigurationDataAccessFactory {
    public ConfigurationDataAccess buildDataAccess(
            ConfigurationDAO configurationDAO,
            IPokemonDataAccess pokemonDataAccess,
            IMoveDataAccess moveDataAccess,
            IItemDataAccess itemDataAccess,
            IAbilityDataAccess abilityDataAccess,
            INatureDataAccess natureDataAccess,
            IStatDataAccess statDataAccess
    ) {
        return new ConfigurationDataAccess( configurationDAO,
                pokemonDataAccess,
                moveDataAccess,
                itemDataAccess,
                abilityDataAccess,
                natureDataAccess,
                statDataAccess );
    }
}
