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
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationPresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.PokemonPresentation;
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
            implements SearchAdapterDecorator.OnItemClickedListener<PokemonPresentation>{
        @Override
        public void onItemClicked(PokemonPresentation item) {
            onPokemonClicked(item);
        }
    }

    public class OnConfigurationClickedListener
            implements SearchAdapterDecorator.OnItemClickedListener<OverviewConfigurationPresentation>{
        @Override
        public void onItemClicked(OverviewConfigurationPresentation item) {
            onConfigurationClicked(item);
        }
    }

    public void onPokemonClicked(PokemonPresentation pokemonPresentation) {
        for( BasePokemon baseItem : pokemonDataList ) {
            if( pokemonPresentation.id == baseItem.getId() ) {
                view.showPokemonDetails( ((Pokemon)baseItem) );
            }
        }
    }

    public void onConfigurationClicked
            (OverviewConfigurationPresentation configurationRepresentation) {
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
            pokemon.setType(Type.STEEL);
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
            ability.setId( 10 );
            ability.setDescriptionShort("Strengthens grass moves to inflict 1.5× damage at 1/3 max HP or less.");
            ability.setName("Magic guard");
            config.setAbility(ability);
            Nature nature = new Nature();
            nature.setName("Modest");
            config.setNature(nature);

            Move move0 = new Move(1);
            move0.setName("Thunderbolt");
            move0.setType(Type.ELECTRIC);
            move0.setCategory(Move.Category.SPECIAL);
            move0.setPower(90);
            config.setMove0(move0);
            Move move1 = new Move(2);
            move1.setName("Grass knot");
            move1.setType(Type.GRASS);
            move1.setCategory(Move.Category.SPECIAL);
            move1.setPower(90);
            config.setMove1(move1);
            Move move2 = new Move(3);
            move2.setName("Signal Beam");
            move2.setType(Type.BUG);
            move2.setCategory(Move.Category.SPECIAL);
            move2.setPower(90);
            config.setMove2(move2);
            Move move3 = new Move(4);
            move3.setName("Hidden power");
            move3.setType(Type.NORMAL);
            move3.setCategory(Move.Category.SPECIAL);
            move3.setPower(90);
            config.setMove3(move3);
            pokemonList.add(pokemonConfig);
        }
        Pokemon pokemon = new Pokemon(1);
        pokemon.setName("Pikachu");
        pokemon.setType(Type.ELECTRIC, Type.FAIRY);
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
