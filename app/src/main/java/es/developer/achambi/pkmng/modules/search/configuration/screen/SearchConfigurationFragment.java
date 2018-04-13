package es.developer.achambi.pkmng.modules.search.configuration.screen;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.DataState;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.details.view.ConfigurationDetailsFragment;
import es.developer.achambi.pkmng.modules.details.view.DetailsUseContext;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation;
import es.developer.achambi.pkmng.modules.search.configuration.adapter.ConfigurationViewHolder;
import es.developer.achambi.pkmng.modules.search.configuration.adapter.SearchConfigurationAdapter;
import es.developer.achambi.pkmng.modules.search.configuration.presenter.SearchConfigurationPresenter;

public class SearchConfigurationFragment extends BaseSearchListFragment
    implements ISearchConfigurationScreen {
    private static final String CURRENT_CONFIGURATION_ARGUMENT_KEY =
            "CURRENT_CONFIGURATION_ARGUMENT_KEY";
    private static final String CONFIGURATION_DETAILS_DIALOG_TAG = "CONFIGURATION_DETAILS_DIALOG_TAG";


    private ConfigurationPresentation configuration;
    private SearchConfigurationAdapter adapter;
    private SearchConfigurationPresenter presenter;

    public static SearchConfigurationFragment newInstance( Bundle args ) {
        SearchConfigurationFragment fragment = new SearchConfigurationFragment();
        fragment.setArguments( args );
        return fragment;
    }

    public static Bundle getFragmentArgs( PokemonConfig currentConfiguration ) {
        Bundle args = new Bundle();
        args.putParcelable( CURRENT_CONFIGURATION_ARGUMENT_KEY, currentConfiguration );
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuration = ConfigurationPresentation.Builder.buildPresentation( getActivity(),
                ((PokemonConfig)getArguments().getParcelable( CURRENT_CONFIGURATION_ARGUMENT_KEY ))
        );
    }

    @Override
    public int getHeaderLayoutResource() {
        return R.layout.pokemon_config_list_item_header_layout;
    }

    @Override
    public void onHeaderSetup(View header) {
        super.onHeaderSetup(header);
        if( !configuration.empty ) {
            ConfigurationViewHolder configurationHolder =
                    new ConfigurationViewHolder( header );
            configurationHolder.linkTo( header );
            configurationHolder.bindTo( configuration );
        } else {
            header.setVisibility( View.GONE );
        }
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if( presenter.getDataState() == DataState.EMPTY
                || presenter.getDataState() == DataState.NOT_FINISHED ) {
            doRequest();
        }
    }

    @Override
    public Presenter setupPresenter() {
        if( presenter == null ) {
            presenter = new SearchConfigurationPresenter( this, MainExecutor.buildExecutor());
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        super.doRequest();
        presenter.fetchConfigurationList(new ResponseHandler<ArrayList<PokemonConfig>>() {
            @Override
            public void onSuccess(Response<ArrayList<PokemonConfig>> response) {
                adapter.setData( PresentationBuilder.buildPresentation( getActivity(),
                        response.getData() ) );
                presentAdapterData();
                hideLoading();
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.setData( PresentationBuilder.buildPresentation( getActivity(),
                presenter.getConfigurationList() ) );
        presentAdapterData();
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        adapter = new SearchConfigurationAdapter( );
        adapter.setListener( presenter );
        return adapter;
    }

    @Override
    public void showConfigurationDetails(PokemonConfig configuration) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ConfigurationDetailsFragment configDetails = ConfigurationDetailsFragment.newInstance(
                configuration, DetailsUseContext.REPLACE_CONTEXT );
        configDetails.show( transaction, CONFIGURATION_DETAILS_DIALOG_TAG );
    }

    @Override
    public Lifecycle screenLifecycle() {
        return getLifecycle();
    }

    public static class PresentationBuilder {
        @NotNull
        public static ArrayList<ConfigurationPresentation> buildPresentation(
                Context context, ArrayList<PokemonConfig> pokemonConfigs ) {
            ArrayList<ConfigurationPresentation> presentations = new ArrayList<>();
            for( PokemonConfig config : pokemonConfigs ) {
                presentations.add( ConfigurationPresentation.Builder.buildPresentation(
                        context, config
                ) );
            }
            return presentations;
        }
    }
}
