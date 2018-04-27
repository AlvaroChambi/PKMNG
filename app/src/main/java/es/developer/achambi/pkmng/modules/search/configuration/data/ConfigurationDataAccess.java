package es.developer.achambi.pkmng.modules.search.configuration.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.configurations;
import es.developer.achambi.pkmng.modules.data.StatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.ability.data.AbilityDataAccess;
import es.developer.achambi.pkmng.modules.search.item.data.ItemDataAccess;
import es.developer.achambi.pkmng.modules.search.move.data.MoveDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.data.NatureDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess;

public class ConfigurationDataAccess {
    private AppDatabase database;
    private PokemonDataAccess pokemonDataAccess;
    private StatDataAccess statDataAccess;
    private ItemDataAccess itemDataAccess;
    private AbilityDataAccess abilityDataAccess;
    private NatureDataAccess natureDataAccess;
    private MoveDataAccess moveDataAccess;

    public ConfigurationDataAccess(AppDatabase database,
                                   PokemonDataAccess pokemonDataAccess,
                                   MoveDataAccess moveDataAccess,
                                   ItemDataAccess itemDataAccess,
                                   AbilityDataAccess abilityDataAccess,
                                   NatureDataAccess natureDataAccess,
                                   StatDataAccess statDataAccess) {
        this.database = database;
        this.pokemonDataAccess = pokemonDataAccess;
        this.statDataAccess = statDataAccess;
        this.moveDataAccess = moveDataAccess;
        this.itemDataAccess = itemDataAccess;
        this.abilityDataAccess = abilityDataAccess;
        this.natureDataAccess = natureDataAccess;
    }

    public ArrayList<PokemonConfig> accessConfigurationData() {
        ArrayList<PokemonConfig> configurationsResult = new ArrayList<>();
        List<configurations> rawConfigurations = database.configurationsModel().getConfigurations();
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

    public int insertConfiguration(final PokemonConfig configuration) {
        configurations configurationToInsert = cast(configuration);
        configurationToInsert.id = null;
        int configurationId = (int)database.configurationsModel().insert( configurationToInsert );
        configuration.setId(configurationId);
        statDataAccess.insertStatsSet( configuration.getId(), configuration.getStatsSet() );
        return configurationId;
    }

    public void updateConfiguration(final PokemonConfig configuration) {
        statDataAccess.updateStatsSet( configuration.getId(), configuration.getStatsSet() );
        database.configurationsModel().update( cast(configuration) );
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
