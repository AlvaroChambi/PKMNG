package es.developer.achambi.pkmng.modules.search.configuration.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.ConfigurationDAO;
import es.developer.achambi.pkmng.core.db.model.configurations;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccess;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess;

public class ConfigurationDataAccess implements IConfigurationDataAccess {
    private ConfigurationDAO configurationDAO;
    private IPokemonDataAccess pokemonDataAccess;
    private IStatDataAccess statDataAccess;
    private IItemDataAccess itemDataAccess;
    private IAbilityDataAccess abilityDataAccess;
    private INatureDataAccess natureDataAccess;
    private IMoveDataAccess moveDataAccess;

    public ConfigurationDataAccess(ConfigurationDAO configurationDAO,
                                   IPokemonDataAccess pokemonDataAccess,
                                   IMoveDataAccess moveDataAccess,
                                   IItemDataAccess itemDataAccess,
                                   IAbilityDataAccess abilityDataAccess,
                                   INatureDataAccess natureDataAccess,
                                   IStatDataAccess statDataAccess) {
        this.configurationDAO = configurationDAO;
        this.pokemonDataAccess = pokemonDataAccess;
        this.statDataAccess = statDataAccess;
        this.moveDataAccess = moveDataAccess;
        this.itemDataAccess = itemDataAccess;
        this.abilityDataAccess = abilityDataAccess;
        this.natureDataAccess = natureDataAccess;
    }

    @Override
    public ArrayList<PokemonConfig> accessConfigurationData() {
        ArrayList<PokemonConfig> configurationsResult = new ArrayList<>();
        List<configurations> rawConfigurations = configurationDAO.getConfigurations();
        for (configurations currentRaw : rawConfigurations) {
            Pokemon pokemon = pokemonDataAccess.accessPokemonData( currentRaw.pokemon_id );
            Configuration configuration = new Configuration();
            configuration.setEvsSet(statDataAccess.accessEvsSetData( currentRaw.id ));
            configuration.setMove0( moveDataAccess.accessMoveData( currentRaw.move_0_id ) );
            configuration.setMove1( moveDataAccess.accessMoveData( currentRaw.move_1_id ) );
            configuration.setMove2( moveDataAccess.accessMoveData( currentRaw.move_2_id ) );
            configuration.setMove3( moveDataAccess.accessMoveData( currentRaw.move_3_id ) );
            configuration.setItem( itemDataAccess.accessItemData( currentRaw.item_id ) );
            configuration.setNature( natureDataAccess.accessNatureDate( currentRaw.nature_id ) );
            configuration.setAbility( abilityDataAccess.accessAbilityData( currentRaw.ability_id ) );
            PokemonConfig pokemonConfig = new PokemonConfig();
            pokemonConfig.setId( currentRaw.id );
            pokemonConfig.setName( currentRaw.name );
            pokemonConfig.setConfiguration( configuration );
            pokemonConfig.setPokemon( pokemon );
            configurationsResult.add( pokemonConfig );
        }
        return configurationsResult;
    }

    @Override
    public int insertConfiguration(final PokemonConfig configuration) throws Error{
        configurations configurationToInsert = cast(configuration);
        configurationToInsert.id = null;
        int configurationId = (int)configurationDAO.insert( configurationToInsert );
        configuration.setId(configurationId);
        statDataAccess.insertStatsSet( configuration.getId(), configuration.getStatsSet() );
        return configurationId;
    }

    @Override
    public void updateConfiguration(final PokemonConfig configuration) {
        statDataAccess.updateStatsSet( configuration.getId(), configuration.getStatsSet() );
        configurationDAO.update( cast(configuration) );
    }

    private configurations cast(PokemonConfig configuration) {
        configurations result = new configurations();
        result.id = configuration.getId();
        result.name = configuration.getName();
        result.pokemon_id = configuration.getPokemon().getId();
        result.ability_id = configuration.getAbility().getId();
        result.item_id = configuration.getItem().getId();
        result.nature_id = configuration.getNature().getId();
        result.move_0_id = configuration.getConfiguration().getMove0().getId();
        result.move_1_id = configuration.getConfiguration().getMove1().getId();
        result.move_2_id = configuration.getConfiguration().getMove2().getId();
        result.move_3_id = configuration.getConfiguration().getMove3().getId();
        return result;
    }
}
