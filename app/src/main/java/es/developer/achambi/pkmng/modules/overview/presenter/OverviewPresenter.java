package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

public class OverviewPresenter implements IOverviewPresenter {
    private static final String TAG = OverviewPresenter.class.getCanonicalName();
    private static final String POKEMON_DATA_SAVED_STATE = "POKEMON_DATA_SAVED_STATE";
    private static final String CONFIGURATION_DATA_SAVED_STATE = "CONFIGURATION_DATA_SAVED_STATE";

    private IOverviewView view;
    private ArrayList<Pokemon> pokemonDataList;
    private ArrayList<PokemonConfig> pokemonConfigList;

    private OnPokemonClickedListener pokemonClickedListener;
    private  OnConfigurationClickedListener configurationClickedListener;

    public OverviewPresenter(IOverviewView view) {
        this.view = view;
        pokemonClickedListener = new OnPokemonClickedListener();
        configurationClickedListener = new OnConfigurationClickedListener();
    }

    @Override
    public void onQueryTextSubmit(String query) {
        Log.i(TAG, "query text submitted: " + query);
    }

    @Override
    public void onQueryTextChanged(String query) {
        Log.i(TAG, "query text changed: " + query);
    }

    @Override
    public ArrayList<Pokemon> fetchPokemonList() {
        pokemonDataList = buildPokemonData();
        return pokemonDataList;
    }

    @Override
    public ArrayList<PokemonConfig> fetchConfigurationList() {
        pokemonConfigList = buildConfigurationData();
        return pokemonConfigList;
    }

    @Override
    public ArrayList<Pokemon> getPokemonList() {
        return pokemonDataList;
    }

    @Override
    public ArrayList<PokemonConfig> getConfigurationList() {
        return pokemonConfigList;
    }

    @Override
    public OnPokemonClickedListener providePokemonListener() {
        return pokemonClickedListener;
    }

    @Override
    public OnConfigurationClickedListener provideConfigurationListener() {
        return configurationClickedListener;
    }

    public class OnPokemonClickedListener
            implements SearchAdapterDecorator.OnItemClickedListener<OverviewPokemonRepresentation>{
        @Override
        public void onItemClicked(OverviewPokemonRepresentation item) {
            onPokemonClicked(item);
        }
    }

    public class OnConfigurationClickedListener
            implements SearchAdapterDecorator.OnItemClickedListener<OverviewConfigurationRepresentation>{
        @Override
        public void onItemClicked(OverviewConfigurationRepresentation item) {
            onConfigurationClicked(item);
        }
    }

    public void onPokemonClicked(OverviewPokemonRepresentation pokemonRepresentation) {
        for( BasePokemon baseItem : pokemonDataList ) {
            if( pokemonRepresentation.id == baseItem.getId() ) {
                view.showPokemonDetails( ((Pokemon)baseItem) );
            }
        }
    }

    public void onConfigurationClicked
            (OverviewConfigurationRepresentation configurationRepresentation) {
        for( BasePokemon baseItem : pokemonConfigList) {
            if( configurationRepresentation.id == baseItem.getId() ) {
                view.showConfigurationDetails( ((PokemonConfig) baseItem) );
            }
        }
    }

    private ArrayList<Pokemon> buildPokemonData( ) {
        int numberOfPokemon = 900;
        ArrayList<Pokemon> pokemonList = new ArrayList<>(numberOfPokemon);
        for(int i = 0; i < numberOfPokemon; i++) {
            Pokemon pokemon = new Pokemon(i);
            pokemon.setName("Pikachu");
            pokemon.setType(Pokemon.Type.ELECTRIC);
            pokemon.setHP(35);
            pokemon.setAttack(55);
            pokemon.setDefense(40);
            pokemon.setSpAttack(50);
            pokemon.setSpDefense(55);
            pokemon.setSpeed(50);

            pokemonList.add(pokemon);
        }
        return pokemonList;
    }

    private ArrayList<PokemonConfig> buildConfigurationData() {
        int numberOfPokemon = 3;
        ArrayList<PokemonConfig> pokemonList = new ArrayList<>(numberOfPokemon);
        for(int i = 0; i < numberOfPokemon; i++) {
            Pokemon pokemon = new Pokemon(i);
            pokemon.setName("Pikachu");
            pokemon.setType(Pokemon.Type.ELECTRIC);
            pokemon.setHP(35);
            pokemon.setAttack(55);
            pokemon.setDefense(40);
            pokemon.setSpAttack(50);
            pokemon.setSpDefense(55);
            pokemon.setSpeed(50);

            Configuration config = new Configuration();
            PokemonConfig pokemonConfig = new PokemonConfig(i,pokemon, config);
            pokemonConfig.setName("Special sweeper awesome pikachu");
            config.setItem("eviolite");
            config.setAbility("Magic guard");
            config.setNature("Modest");

            config.setMove0("Thunderbolt");
            config.setMove1("Grass Knot");
            config.setMove2("Signal Beam");
            config.setMove3("Hidden Power");
            pokemonList.add(pokemonConfig);
        }
        return pokemonList;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(POKEMON_DATA_SAVED_STATE, pokemonDataList);
        bundle.putParcelableArrayList(CONFIGURATION_DATA_SAVED_STATE, pokemonConfigList);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        pokemonDataList = bundle.getParcelableArrayList(POKEMON_DATA_SAVED_STATE);
        pokemonConfigList = bundle.getParcelableArrayList(CONFIGURATION_DATA_SAVED_STATE);
    }
}
