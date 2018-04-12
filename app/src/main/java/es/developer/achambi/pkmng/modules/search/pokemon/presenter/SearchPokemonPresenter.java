package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.Error;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.DataState;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.view.ISearchPokemonScreen;
import es.developer.achambi.pkmng.modules.search.pokemon.view.presentation.PokemonPresentation;

public class SearchPokemonPresenter extends Presenter implements
        SearchAdapterDecorator.OnItemClickedListener<PokemonPresentation> {
    private static final String POKEMON_DATA_SAVED_STATE = "POKEMON_DATA_SAVED_STATE";

    private ArrayList<Pokemon> pokemonDataList;
    private ISearchPokemonScreen view;
    private PokemonDataAccess pokemonDataAccess;

    public SearchPokemonPresenter( ISearchPokemonScreen view ) {
        super(view);
        this.view = view;
        pokemonDataList = new ArrayList<>();
        pokemonDataAccess = new PokemonDataAccess();
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
        setDataState(DataState.NOT_FINISHED);
        ResponseHandler handler =
                new ResponseHandlerDecorator<ArrayList<Pokemon>>( responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Pokemon>> response) {
                super.onSuccess(response);
                pokemonDataList = response.getData();
                setDataState( DataState.SUCCESS );
            }

            @Override
            public void onError(Error error) {
                super.onError(error);
                setDataState( DataState.ERROR );
            }
        };

        request( new Request() {
            @Override
            public Response perform() {
                return new Response<>( pokemonDataAccess.accessData() );
            }
        }, handler );
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(POKEMON_DATA_SAVED_STATE, pokemonDataList);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        pokemonDataList = bundle.getParcelableArrayList(POKEMON_DATA_SAVED_STATE);
    }
}
