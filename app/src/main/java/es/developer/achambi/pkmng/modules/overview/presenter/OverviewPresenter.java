package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private IOverviewView view;
    private List<Pokemon> pokemonDataList;
    private List<PokemonConfig> pokemonConfigList;

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
    public List<Pokemon> fetchPokemonList() {
        if(pokemonDataList == null) {
            pokemonDataList = buildPokemonData( );
        }
        return pokemonDataList;
    }

    @Override
    public OnPokemonClickedListener providePokemonListener() {
        return pokemonClickedListener;
    }

    @Override
    public OnConfigurationClickedListener provideConfigurationListener() {
        return configurationClickedListener;
    }

    @Override
    public List<PokemonConfig> fetchConfigurationList() {
        if (pokemonConfigList == null) {
            pokemonConfigList = buildConfigurationData();
        }
        return pokemonConfigList;
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

    private List<Pokemon> buildPokemonData( ) {
        int numberOfPokemon = 900;
        List<Pokemon> pokemonList = new ArrayList<>(numberOfPokemon);
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

    private List<PokemonConfig> buildConfigurationData() {
        int numberOfPokemon = 3;
        List<PokemonConfig> pokemonList = new ArrayList<>(numberOfPokemon);
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
            config.setItem("Eviolite");
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
        bundle.putParcelableArrayList(DATA_SAVED_STATE, (ArrayList)pokemonDataList);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        pokemonDataList = bundle.getParcelableArrayList(DATA_SAVED_STATE);
    }
}
