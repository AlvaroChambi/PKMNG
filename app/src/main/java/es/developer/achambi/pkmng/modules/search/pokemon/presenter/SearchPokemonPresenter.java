package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.pokemon.view.ISearchPokemonScreen;
import es.developer.achambi.pkmng.modules.search.pokemon.view.presentation.PokemonPresentation;

public class SearchPokemonPresenter extends Presenter implements
        SearchAdapterDecorator.OnItemClickedListener<PokemonPresentation> {
    private static final String POKEMON_DATA_SAVED_STATE = "POKEMON_DATA_SAVED_STATE";

    private ArrayList<Pokemon> pokemonDataList;
    private ISearchPokemonScreen view;

    public SearchPokemonPresenter(ISearchPokemonScreen view, Screen screen) {
        super(screen);
        this.view = view;
        pokemonDataList = new ArrayList<>();
    }

    @Override
    public void onItemClicked(PokemonPresentation item) {
        for( BasePokemon baseItem : pokemonDataList ) {
            if( item.id == baseItem.getId() ) {
                view.showPokemonDetails( ((Pokemon)baseItem) );
            }
        }
    }

    public ArrayList<Pokemon> getPokemonList() {
        return pokemonDataList;
    }

    public void fetchPokemonList(final ResponseHandler<ArrayList<Pokemon>> responseHandler ) {
        ResponseHandler handler =
                new ResponseHandlerDecorator<ArrayList<Pokemon>>( responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Pokemon>> response) {
                super.onSuccess(response);
                pokemonDataList = response.getData();
            }
        };

        request( new Request() {
            @Override
            public Response perform() throws Exception {
                return new Response<>( buildPokemonData() );
            }
        }, handler );
    }

    private ArrayList<Pokemon> buildPokemonData( ) throws Exception {
        Thread.sleep(3000);
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

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(POKEMON_DATA_SAVED_STATE, pokemonDataList);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        pokemonDataList = bundle.getParcelableArrayList(POKEMON_DATA_SAVED_STATE);
    }
}
