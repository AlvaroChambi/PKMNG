package es.developer.achambi.pkmng.modules.overview.screen;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.core.threading.Error;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.DataState;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.create.screen.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.details.view.ConfigurationDetailsFragment;
import es.developer.achambi.pkmng.modules.details.view.DetailsUseContext;
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenter;
import es.developer.achambi.pkmng.modules.search.pokemon.adapter.PokemonSearchAdapter;
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.modules.search.configuration.adapter.SearchConfigurationAdapter;

public class OverviewFragment extends BaseSearchListFragment implements IOverviewScreen {
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CONFIGURATION_DETAILS_DIALOG_TAG = "CONFIGURATION_DETAILS_DIALOG_TAG";

    private static final int CREATE_CONFIGURATION_REQUEST_CODE = 101;
    private static final int UPDATE_CONFIGURATION_REQUEST_CODE = 102;

    private OverviewPresenter presenter;
    private PokemonSearchAdapter pokemonSearchAdapter;
    private SearchConfigurationAdapter configurationSearchAdapter;

    public static OverviewFragment newInstance( Bundle args ) {
        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestManager = Glide.with(this);
    }

    @Override
    public void onViewSetup(View view, Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if( presenter.getDataState() == DataState.EMPTY
                || presenter.getDataState() == DataState.NOT_FINISHED ) {
            loadOverviewListData();
        }
    }

    @Override
    public Presenter setupPresenter() {
        if(presenter == null) {
            presenter = AppWiring.overviewAssembler.getPresenterFactory().buildPresenter(this);
        }
        return presenter;
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        pokemonSearchAdapter = new PokemonSearchAdapter(requestManager);
        pokemonSearchAdapter.setListener( presenter.getPokemonPresenter() );

        configurationSearchAdapter = new SearchConfigurationAdapter( pokemonSearchAdapter,
                requestManager );
        configurationSearchAdapter.setListener( presenter.getConfigurationPresenter() );

        return configurationSearchAdapter;
    }

    private void loadOverviewListData() {
        startLoading();
        presenter.fetchPokemonList(new ResponseHandler<ArrayList<Pokemon>>() {
            @Override
            public void onSuccess(Response<ArrayList<Pokemon>> response) {
                pokemonSearchAdapter.setData( PresentationBuilder.buildPokemonPresentation  (
                        getActivity(), response.getData() ) );
                if( presenter.getDataState() == DataState.SUCCESS ) {
                    presentAdapterData();
                    hideLoading();
                }
            }

            @Override
            public void onError(Error error) {
                super.onError(error);
                showError( error );
            }
        });
        presenter.fetchConfigurationList(new ResponseHandler<ArrayList<PokemonConfig>>() {
            @Override
            public void onSuccess(Response<ArrayList<PokemonConfig>> response) {
                configurationSearchAdapter.setData(
                        PresentationBuilder.buildConfigurationPresentation(
                                getActivity(), response.getData() ) );
                if( presenter.getDataState() == DataState.SUCCESS ) {
                    presentAdapterData();
                    hideLoading();
                }
            }

            @Override
            public void onError(Error error) {
                super.onError(error);
                showError( error );
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pokemonSearchAdapter.setData( PresentationBuilder.buildPokemonPresentation  (
                getActivity(), presenter.getPokemonList() ) );
        configurationSearchAdapter.setData( PresentationBuilder.buildConfigurationPresentation(
                getActivity(), presenter.getConfigurationList() ) );
        presentAdapterData();
    }

    @Override
    public void showPokemonDetails(Pokemon pokemon) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PokemonDetailsFragment detailsFragment =
                PokemonDetailsFragment.newInstance( pokemon, DetailsUseContext.SELECT_CONTEXT);
        detailsFragment.setTargetFragment( this, CREATE_CONFIGURATION_REQUEST_CODE );
        detailsFragment.show( transaction, POKEMON_DETAILS_DIALOG_TAG );
    }

    @Override
    public void showConfigurationDetails(PokemonConfig configuration) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ConfigurationDetailsFragment configDetails = ConfigurationDetailsFragment.newInstance(
                configuration, DetailsUseContext.SELECT_CONTEXT );
        configDetails.setTargetFragment( this, UPDATE_CONFIGURATION_REQUEST_CODE );
        configDetails.show( transaction, CONFIGURATION_DETAILS_DIALOG_TAG );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == Activity.RESULT_OK && requestCode == CREATE_CONFIGURATION_REQUEST_CODE ) {
            PokemonConfig pokemonConfig = data.getParcelableExtra(
                    ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY );

            presenter.onConfigurationCreated(pokemonConfig);

            configurationSearchAdapter.setData( PresentationBuilder.buildConfigurationPresentation(
                    getActivity(), presenter.getConfigurationList() ) );
            presentAdapterData();
        } else if( resultCode == Activity.RESULT_OK &&
                requestCode == UPDATE_CONFIGURATION_REQUEST_CODE ) {
            PokemonConfig pokemonConfig = data.getParcelableExtra(
                    ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY );
            presenter.onConfigurationUpdated(pokemonConfig);

            configurationSearchAdapter.setData( PresentationBuilder.buildConfigurationPresentation(
                    getActivity(), presenter.getConfigurationList() ) );
            presentAdapterData();
        }
    }

    @Override
    public Lifecycle screenLifecycle() {
        return getLifecycle();
    }

    @Override
    public void onQueryTextSubmitted(String query) {
        startLoading();
        presenter.fetchPokemonQuery( query, new ResponseHandler<ArrayList<Pokemon>>() {
            @Override
            public void onSuccess(Response<ArrayList<Pokemon>> response) {
                pokemonSearchAdapter.setData( PresentationBuilder.buildPokemonPresentation  (
                        getActivity(), response.getData() ) );
                if( presenter.getDataState() == DataState.SUCCESS ) {
                    presentAdapterData();
                    hideLoading();
                }
            }

            @Override
            public void onError(Error error) {
                super.onError(error);
                showError( error );
            }
        });

        presenter.fetchConfigurationQuery( query, new ResponseHandler<ArrayList<PokemonConfig>>() {
            @Override
            public void onSuccess(Response<ArrayList<PokemonConfig>> response) {
                configurationSearchAdapter.setData(
                        PresentationBuilder.buildConfigurationPresentation(
                                getActivity(), response.getData() ) );
                if( presenter.getDataState() == DataState.SUCCESS ) {
                    presentAdapterData();
                    hideLoading();
                }
            }

            @Override
            public void onError(Error error) {
                super.onError(error);
                showError( error );
            }
        });
    }

    @Override
    public void onSearchFinished() {
        loadOverviewListData();
    }

    private static class PresentationBuilder {
        @NotNull
        public static ArrayList<PokemonPresentation> buildPokemonPresentation(
                Context context, ArrayList<Pokemon> pokemonList ) {
            ArrayList<PokemonPresentation> presentations = new ArrayList<>();
            for( Pokemon pokemon : pokemonList ) {
                presentations.add( PokemonPresentation.Builder
                        .buildPresentation( context, pokemon ) );
            }

            return presentations;
        }

        @NotNull
        public static ArrayList<ConfigurationPresentation> buildConfigurationPresentation(
                Context context, ArrayList<PokemonConfig> configurations ) {
            ArrayList<ConfigurationPresentation> presentations = new ArrayList<>();
            for( PokemonConfig configuration : configurations ) {
                presentations.add( ConfigurationPresentation.Builder
                        .buildPresentation( context, configuration ) );
            }

            return presentations;
        }
    }
}
