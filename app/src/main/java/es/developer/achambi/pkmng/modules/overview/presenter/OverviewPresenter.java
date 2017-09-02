package es.developer.achambi.pkmng.modules.overview.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.IOverviewView;

public class OverviewPresenter implements IOverviewPresenter{
    private static final String TAG = OverviewPresenter.class.getCanonicalName();
    private IOverviewView view;

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
    public List<BasePokemon> getPokemonList() {
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
                PokemonConfig config = new PokemonConfig(pokemon);
                config.setName("Special sweeper awesone pikachu");
                config.setItem("Item: Eviolite");
                config.setAbility("Ability: Magic guard");
                config.setNature("Nature: Modest");
                pokemonList.add(config);
            } else {
                pokemonList.add(pokemon);
            }
        }
        return pokemonList;
    }
}
