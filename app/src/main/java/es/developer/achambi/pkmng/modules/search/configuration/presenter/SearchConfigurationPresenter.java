package es.developer.achambi.pkmng.modules.search.configuration.presenter;

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
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation;
import es.developer.achambi.pkmng.modules.search.configuration.screen.ISearchConfigurationScreen;

public class SearchConfigurationPresenter extends Presenter implements
        SearchAdapterDecorator.OnItemClickedListener<ConfigurationPresentation> {
    private static final String CONFIGURATION_DATA_SAVED_STATE = "CONFIGURATION_DATA_SAVED_STATE";

    private ArrayList<PokemonConfig> pokemonConfigList;
    private ISearchConfigurationScreen screen;
    private IConfigurationDataAccess dataAccess;

    public SearchConfigurationPresenter( ISearchConfigurationScreen screen,
                                         IConfigurationDataAccess dataAccess,
                                         MainExecutor executor ) {
        super(screen, executor);
        this.screen = screen;
        this.dataAccess = dataAccess;
    }

    public void fetchConfigurationsQuery( final String query,
            final ResponseHandler<ArrayList<PokemonConfig>> responseHandler ) {
        setDataState( DataState.NOT_FINISHED );
        ResponseHandler handler = new ResponseHandlerDecorator<ArrayList<PokemonConfig>>(
                responseHandler) {
            @Override
            public void onSuccess(Response<ArrayList<PokemonConfig>> response) {
                setDataState( DataState.SUCCESS );
                pokemonConfigList = response.getData();
                super.onSuccess(response);
            }

            @Override
            public void onError(Error error) {
                setDataState( DataState.ERROR );
                super.onError(error);
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.queryConfigurationData( query ) );
            }
        }, handler );
    }

    public void fetchConfigurationList(
            final ResponseHandler<ArrayList<PokemonConfig>> responseHandler ) {
        setDataState( DataState.NOT_FINISHED );
        ResponseHandler handler = new ResponseHandlerDecorator<ArrayList<PokemonConfig>>(
                responseHandler) {
            @Override
            public void onSuccess(Response<ArrayList<PokemonConfig>> response) {
                setDataState( DataState.SUCCESS );
                pokemonConfigList = response.getData();
                super.onSuccess(response);
            }

            @Override
            public void onError(Error error) {
                setDataState( DataState.ERROR );
                super.onError(error);
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessConfigurationData() );
            }
        }, handler );
    }

    public ArrayList<PokemonConfig> getConfigurationList() {
        return pokemonConfigList;
    }

    @Override
    public void onItemClicked(ConfigurationPresentation item) {
        for( BasePokemon baseItem : pokemonConfigList) {
            if( item.id == baseItem.getId() ) {
                screen.showConfigurationDetails( ((PokemonConfig) baseItem) );
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList( CONFIGURATION_DATA_SAVED_STATE, pokemonConfigList );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        pokemonConfigList = bundle.getParcelableArrayList( CONFIGURATION_DATA_SAVED_STATE );
    }
}
