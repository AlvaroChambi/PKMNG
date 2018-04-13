package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.Error;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
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
import es.developer.achambi.pkmng.modules.search.pokemon.screen.ISearchPokemonScreen;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;

public class SearchPokemonPresenter extends Presenter implements
        SearchAdapterDecorator.OnItemClickedListener<PokemonPresentation> {
    private static final String POKEMON_DATA_SAVED_STATE = "POKEMON_DATA_SAVED_STATE";

    private ArrayList<Pokemon> pokemonDataList;
    private ISearchPokemonScreen screen;
    private PokemonDataAccess pokemonDataAccess;

    public SearchPokemonPresenter(ISearchPokemonScreen screen,
                                  PokemonDataAccess dataAccess,
                                  MainExecutor executor ) {
        super(screen, executor);
        this.screen = screen;
        pokemonDataList = new ArrayList<>();
        this.pokemonDataAccess = dataAccess;
    }

    @Override
    public void onItemClicked(PokemonPresentation item) {
        for( BasePokemon baseItem : pokemonDataList ) {
            if( item.id == baseItem.getId() ) {
                screen.showPokemonDetails( ((Pokemon)baseItem) );
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
                setDataState( DataState.SUCCESS );
                pokemonDataList = response.getData();
                super.onSuccess(response);
            }

            @Override
            public void onError(Error error) {
                setDataState( DataState.ERROR );
                super.onError(error);
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
        super.onRestoreInstanceState(bundle);
        pokemonDataList = bundle.getParcelableArrayList(POKEMON_DATA_SAVED_STATE);
    }
}
