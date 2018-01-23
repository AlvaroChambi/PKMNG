package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

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
    public void onConfigurationCreated(PokemonConfig config) {
        pokemonConfigList.add( config );
    }

    @Override
    public void onConfigurationUpdated(PokemonConfig config) {
        int indexToReplace = pokemonConfigList.indexOf( config );
        pokemonConfigList.set( indexToReplace, config );
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
            pokemon.setType(Type.ELECTRIC);
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
        int numberOfPokemon = 1;
        ArrayList<PokemonConfig> pokemonList = new ArrayList<>(numberOfPokemon);
        for(int i = 0; i < numberOfPokemon; i++) {
            Pokemon pokemon = new Pokemon(i);
            pokemon.setName("Pikachu");
            pokemon.setType(Type.ELECTRIC);
            pokemon.setHP(35);
            pokemon.setAttack(55);
            pokemon.setDefense(40);
            pokemon.setSpAttack(50);
            pokemon.setSpDefense(55);
            pokemon.setSpeed(50);

            Configuration config = new Configuration();
            PokemonConfig pokemonConfig = new PokemonConfig(i,pokemon, config);
            pokemonConfig.setName("Special");
            Item item = new Item();
            item.setName("eviolite");
            config.setItem( item );
            Ability ability = new Ability();
            ability.setName("Magic guard");
            config.setAbility(ability);
            Nature nature = new Nature();
            nature.setName("Modest");
            config.setNature(nature);

            Move move0 = new Move();
            move0.setName("Thunderbolt");
            move0.setType(Type.ELECTRIC);
            config.setMove0(move0);
            Move move1 = new Move();
            move1.setName("Grass knot");
            move1.setType(Type.ELECTRIC);
            config.setMove1(move1);
            Move move2 = new Move();
            move2.setName("Signal Beam");
            move2.setType(Type.ELECTRIC);
            config.setMove2(move2);
            Move move3 = new Move();
            move3.setName("Hidden power");
            move3.setType(Type.ELECTRIC);
            config.setMove3(move3);
            pokemonList.add(pokemonConfig);
        }
        Pokemon pokemon = new Pokemon(1);
        pokemon.setName("Pikachu");
        pokemon.setType(Type.ELECTRIC);
        pokemon.setHP(35);
        pokemon.setAttack(55);
        pokemon.setDefense(40);
        pokemon.setSpAttack(50);
        pokemon.setSpDefense(55);
        pokemon.setSpeed(50);
        pokemonList.add(new PokemonConfig(110, pokemon, new Configuration()));
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
