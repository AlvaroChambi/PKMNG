package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;
import es.developer.achambi.pkmng.modules.overview.view.adapter.OverviewListAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewViewDataBuilder;

public class OverviewPresenter implements IOverviewPresenter {
    private static final String TAG = OverviewPresenter.class.getCanonicalName();
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private IOverviewView view;
    private List<BasePokemon> dataList;

    public OverviewPresenter(IOverviewView view) {
        this.view = view;
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
    public List<BasePokemon> fetchPokemonList() {
        if(dataList == null) {
            dataList = buildPokemonData( SearchFilter.ALL_FILTER );
        }
        return dataList;
    }

    @Override
    public List<BasePokemon> fetchPokemonList( SearchFilter searchFilter ) {
        if(dataList == null) {
            dataList = buildPokemonData( searchFilter );
        }
        return dataList;
    }

    @Override
    public void onPokemonClicked(OverviewPokemonRepresentation pokemonRepresentation) {
        for( BasePokemon baseItem : dataList ) {
            if( pokemonRepresentation.id == baseItem.getId() ) {
                view.showPokemonDetails( ((Pokemon)baseItem) );
            }
        }
    }

    @Override
    public void onConfigurationClicked
            (OverviewConfigurationRepresentation configurationRepresentation) {
        for( BasePokemon baseItem : dataList ) {
            if( configurationRepresentation.id == baseItem.getId() ) {
                view.showConfigurationDetails( ((PokemonConfig) baseItem) );
            }
        }
    }

    private List<BasePokemon> buildPokemonData( SearchFilter searchFilter ) {
        int numberOfPokemon = 900;
        List<BasePokemon> pokemonList = new ArrayList<>(numberOfPokemon);
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

            if( i < 5 ) {
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

                if(pokemonConfig.isIncluded( searchFilter )) {
                    pokemonList.add(pokemonConfig);
                }
            } else {
                if(pokemon.isIncluded( searchFilter )) {
                    pokemonList.add(pokemon);
                }
            }
        }
        return pokemonList;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(DATA_SAVED_STATE, (ArrayList)dataList);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        dataList = bundle.getParcelableArrayList(DATA_SAVED_STATE);
    }
}
