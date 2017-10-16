package es.developer.achambi.pkmng.modules.overview.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewViewDataBuilder;

public class OverviewPresenter implements IOverviewPresenter{
    private static final String TAG = OverviewPresenter.class.getCanonicalName();
    private IOverviewView view;
    private List<BasePokemon> dataList;
    private OverviewViewDataBuilder viewDataAdapter;

    public OverviewPresenter(IOverviewView view) {
        this.view = view;
        viewDataAdapter = new OverviewViewDataBuilder();
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
        dataList = buildPokemonData();
        return dataList;
    }

    private List<BasePokemon> buildPokemonData() {
        List<BasePokemon> pokemonList = new ArrayList<>(800);
        for(int i = 0; i < 800; i++) {
            Pokemon pokemon = new Pokemon();
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
                PokemonConfig pokemonConfig = new PokemonConfig(pokemon, config);
                pokemonConfig.setName("Special sweeper awesome pikachu");
                config.setItem("Eviolite");
                config.setAbility("Magic guard");
                config.setNature("Modest");
                pokemonList.add(pokemonConfig);
            } else {
                pokemonList.add(pokemon);
            }
        }
        return pokemonList;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {

    }
}
