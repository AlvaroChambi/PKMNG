package es.developer.achambi.pkmng.modules.create.presenter;

import android.os.Bundle;

import es.developer.achambi.pkmng.modules.create.view.StatEVView;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class ConfigurationPresenter implements ICreateConfigurationPresenter,
        StatEVView.ProgressUpdateProvider {

    private static final String CONFIGURATION_SAVED_DATA_TAG = "CONFIGURATION_SAVED_DATA_TAG";
    private static final String ACTUAL_CONFIGURATION_SAVED_DATA_TAG = "ACTUAL_CONFIGURATION_SAVED_DATA_TAG";
    private static final String POKEMON_SAVED_DATA_TAG = "POKEMON_SAVED_DATA_TAG";

    private Pokemon editablePokemon;
    private Configuration editableConfiguration;
    private PokemonConfig pokemonConfiguration;

    public ConfigurationPresenter( ) {
        editableConfiguration = new Configuration();
        editablePokemon = new Pokemon(1);
    }

    @Override
    public int requestValueIncrement(Stat stat, int progress) {
        StatsSet evData = editableConfiguration.getStatsSet();
        int totalStatsPreview = evData.getTotalStatsPreview( stat, progress );
        if( totalStatsPreview <= StatsSet.MAX_TOTAL_EVS ) {
            evData.getStats().put(stat, progress);
            return progress;
        } else if( totalStatsPreview > StatsSet.MAX_TOTAL_EVS ){
            return progress + ( StatsSet.MAX_TOTAL_EVS - totalStatsPreview );
        }
        return progress;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable( POKEMON_SAVED_DATA_TAG, editablePokemon);
        bundle.putParcelable( CONFIGURATION_SAVED_DATA_TAG, editableConfiguration);
        bundle.putParcelable( ACTUAL_CONFIGURATION_SAVED_DATA_TAG, pokemonConfiguration );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        editablePokemon = bundle.getParcelable( POKEMON_SAVED_DATA_TAG );
        editableConfiguration = bundle.getParcelable( CONFIGURATION_SAVED_DATA_TAG );
        pokemonConfiguration = bundle.getParcelable( ACTUAL_CONFIGURATION_SAVED_DATA_TAG );
    }

    @Override
    public StatsSet getEvSet() {
        return editableConfiguration.getStatsSet();
    }

    @Override
    public ConfigurationAction saveConfiguration(String name) {
        if( pokemonConfiguration == null ) {
            PokemonConfig config = new PokemonConfig( 1002, editablePokemon, editableConfiguration);
            config.setName( name );
            pokemonConfiguration = config;
            return ConfigurationAction.CREATED;
        } else if( pokemonConfiguration.getName().equals( name ) &&
               pokemonConfiguration.getPokemon().getName().equals(editablePokemon.getName()) &&
               pokemonConfiguration.getConfiguration().equals(editableConfiguration)  ) {
            return ConfigurationAction.NONE;
        } else {
            pokemonConfiguration.setName( name );
            pokemonConfiguration.setPokemon( editablePokemon );
            pokemonConfiguration.setConfiguration( editableConfiguration );
            return ConfigurationAction.UPDATED;
        }
    }

    public void setPokemonConfiguration(PokemonConfig pokemonConfiguration) {
        this.pokemonConfiguration = pokemonConfiguration;
        this.editablePokemon = new Pokemon( pokemonConfiguration.getPokemon() );
        this.editableConfiguration = new Configuration( pokemonConfiguration.getConfiguration() );
    }

    public PokemonConfig getPokemonConfiguration() {
        return pokemonConfiguration;
    }

    /**
     *
     * @return configuration name if there are a previously created configuration, empty string
     * otherwise
     */
    public String getConfigurationName() {
        if(pokemonConfiguration == null) {
            return "";
        } else {
            return pokemonConfiguration.getName();
        }
    }

    public Configuration getConfiguration() {
        return editableConfiguration;
    }

    public void setPokemon(Pokemon pokemon) {
        this.editablePokemon = pokemon;
    }

    public Pokemon getPokemon() {
        return editablePokemon;
    }

    public Item getItem() {
        return editableConfiguration.getItem();
    }

    public void setItem(Item item) {
        this.editableConfiguration.setItem( item );
    }

    public Ability getAbility() {
        return editableConfiguration.getAbility();
    }

    public void setAbility(Ability ability) {
        this.editableConfiguration.setAbility( ability );
    }

    public Nature getNature() {
        return editableConfiguration.getNature();
    }

    public void setNature(Nature nature) {
        editableConfiguration.setNature( nature );
    }

    public void setMove0(Move move0) {
        editableConfiguration.setMove0( move0 );
    }

    public void setMove1(Move move1) {
        editableConfiguration.setMove1( move1 );
    }

    public void setMove2(Move move2) {
        editableConfiguration.setMove2( move2 );
    }

    public void setMove3(Move move3) {
        editableConfiguration.setMove3( move3 );
    }
}