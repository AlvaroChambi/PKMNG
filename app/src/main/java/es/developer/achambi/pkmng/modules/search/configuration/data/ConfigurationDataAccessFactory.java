package es.developer.achambi.pkmng.modules.search.configuration.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.modules.data.IStatDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccessFactory;

public class ConfigurationDataAccessFactory implements IConfigurationDataAccessFactory{
    private AppDatabase database;
    private IPokemonDataAccessFactory pokemonDataAccessFactory;
    private IStatDataAccessFactory statDataAccessFactory;
    private IMoveDataAccessFactory moveDataAccessFactory;
    private IItemDataAccessFactory itemDataAccessFactory;
    private INatureDataAccessFactory natureDataAccessFactory;
    private IAbilityDataAccessFactory abilityDataAccessFactory;

    public ConfigurationDataAccessFactory(AppDatabase database,
                                          IPokemonDataAccessFactory pokemonDataAccessFactory,
                                          IStatDataAccessFactory statDataAccessFactory,
                                          IMoveDataAccessFactory moveDataAccessFactory,
                                          IItemDataAccessFactory itemDataAccessFactory,
                                          INatureDataAccessFactory natureDataAccessFactory,
                                          IAbilityDataAccessFactory abilityDataAccessFactory) {
        this.database = database;
        this.pokemonDataAccessFactory = pokemonDataAccessFactory;
        this.statDataAccessFactory = statDataAccessFactory;
        this.moveDataAccessFactory = moveDataAccessFactory;
        this.itemDataAccessFactory = itemDataAccessFactory;
        this.natureDataAccessFactory = natureDataAccessFactory;
        this.abilityDataAccessFactory = abilityDataAccessFactory;
    }

    @Override
    public ConfigurationDataAccess buildDataAccess() {
        return new ConfigurationDataAccess( database,
                pokemonDataAccessFactory.buildDataAccess(),
                moveDataAccessFactory.buildDataAccess(),
                itemDataAccessFactory.buildDataAccess(),
                abilityDataAccessFactory.buildDataAccess(),
                natureDataAccessFactory.buildDataAccess(),
                statDataAccessFactory.buildDataAccess());
    }
}
