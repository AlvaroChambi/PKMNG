package es.developer.achambi.pkmng.modules.search.pokemon.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.coreframework.threading.Error;
import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.coreframework.threading.Request;
import es.developer.achambi.coreframework.threading.Response;
import es.developer.achambi.coreframework.threading.ResponseHandler;
import es.developer.achambi.coreframework.threading.ResponseHandlerDecorator;
import es.developer.achambi.coreframework.ui.DataState;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.ISearchPokemonScreen;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;

public class SearchPokemonPresenter extends Presenter implements
        SearchAdapterDecorator.OnItemClickedListener<PokemonPresentation> {
    private static final String POKEMON_DATA_SAVED_STATE = "POKEMON_DATA_SAVED_STATE";

    private ArrayList<Pokemon> pokemonDataList;
    private ISearchPokemonScreen screen;
    private IPokemonDataAccess pokemonDataAccess;

    public SearchPokemonPresenter( ISearchPokemonScreen screen,
                                   IPokemonDataAccess dataAccess,
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

    public void fetchPokemonList( final ResponseHandler<ArrayList<Pokemon>> responseHandler ) {
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

    public void fetchPokemonQuery(final ResponseHandler<ArrayList<Pokemon>> responseHandler,
                                  final String query) {
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
                return new Response<>( pokemonDataAccess.queryData( query ) );
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
