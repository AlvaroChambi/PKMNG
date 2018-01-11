package es.developer.achambi.pkmng.modules.create.presenter;

import android.os.Bundle;
import android.widget.Toast;

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

    private Pokemon pokemon;
    private Configuration configuration;
    private PokemonConfig pokemonConfiguration;

    public ConfigurationPresenter( ) {
        pokemonConfiguration = new PokemonConfig( 1003, new Pokemon(1), new Configuration() );
        configuration = new Configuration();
        pokemon = new Pokemon(1);
    }

    @Override
    public int requestValueIncrement(Stat stat, int progress) {
        StatsSet evData = configuration.getStatsSet();
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
        bundle.putParcelable( POKEMON_SAVED_DATA_TAG, pokemon );
        bundle.putParcelable( CONFIGURATION_SAVED_DATA_TAG, configuration );
        bundle.putParcelable( ACTUAL_CONFIGURATION_SAVED_DATA_TAG, pokemonConfiguration );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        pokemon = bundle.getParcelable( POKEMON_SAVED_DATA_TAG );
        configuration = bundle.getParcelable( CONFIGURATION_SAVED_DATA_TAG );
        pokemonConfiguration = bundle.getParcelable( ACTUAL_CONFIGURATION_SAVED_DATA_TAG );
    }

    @Override
    public StatsSet getEvSet() {
        return configuration.getStatsSet();
    }

    @Override
    public PokemonConfig createConfiguration(String name) {
        PokemonConfig pokemonConfig = new PokemonConfig( 1001, pokemon, configuration );
        pokemonConfig.setName( name );

        if( pokemonConfiguration.equals( pokemonConfig ) ) {
            return null;
        } else {
            return pokemonConfig;
        }
    }

    @Override
    public boolean saveConfiguration() {
        PokemonConfig config = new PokemonConfig( 1002, pokemon, configuration );
        if( !pokemonConfiguration.equals( config ) ) {
            return true;
        } else {
            return false;
        }
    }

    public void setPokemonConfiguration(PokemonConfig pokemonConfiguration) {
        this.pokemonConfiguration = pokemonConfiguration;
        this.pokemon = new Pokemon( pokemonConfiguration.getPokemon() );
        this.configuration = new Configuration( pokemonConfiguration.getConfiguration() );
    }

    public String getConfigurationName() {
        return pokemonConfiguration.getName();
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public Item getItem() {
        return configuration.getItem();
    }

    public void setItem(Item item) {
        this.configuration.setItem( item );
    }

    public Ability getAbility() {
        return configuration.getAbility();
    }

    public void setAbility(Ability ability) {
        this.configuration.setAbility( ability );
    }

    public Nature getNature() {
        return configuration.getNature();
    }

    public void setNature(Nature nature) {
        configuration.setNature( nature );
    }

    public void setMove0(Move move0) {
        configuration.setMove0( move0 );
    }

    public void setMove1(Move move1) {
        configuration.setMove1( move1 );
    }

    public void setMove2(Move move2) {
        configuration.setMove2( move2 );
    }

    public void setMove3(Move move3) {
        configuration.setMove3( move3 );
    }
}
