package es.developer.achambi.pkmng.modules.search.pokemon.view;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.threading.Error;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.details.view.DetailsUseContext;
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.pokemon.adapter.PokemonSearchAdapter;
import es.developer.achambi.pkmng.modules.search.pokemon.view.presentation.PokemonPresentation;
import es.developer.achambi.pkmng.modules.search.pokemon.adapter.PokemonViewHolder;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenter;

public class SearchPokemonFragment extends BaseSearchListFragment implements ISearchPokemonScreen {
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CURRENT_POKEMON_ARGUMENT_KEY = "CURRENT_POKEMON_ARGUMENT_KEY";

    private SearchPokemonPresenter presenter;
    private PokemonSearchAdapter adapter;
    private PokemonPresentation pokemon;

    public static SearchPokemonFragment newInstance( Bundle args ) {
        SearchPokemonFragment fragment = new SearchPokemonFragment();
        fragment.setArguments( args );
        return fragment;
    }

    public static Bundle getFragmentArgs( Pokemon pokemon ) {
        Bundle bundle = new Bundle();
        bundle.putParcelable( CURRENT_POKEMON_ARGUMENT_KEY, pokemon );
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokemon = PokemonPresentation.Builder.buildPresentation( getActivity(),
                ((Pokemon)getArguments().getParcelable( CURRENT_POKEMON_ARGUMENT_KEY ))
        );
    }

    @Override
    public int getHeaderLayoutResource() {
        return R.layout.pokemon_list_item_header_layout;
    }

    @Override
    public void onHeaderSetup(View header) {
        super.onHeaderSetup(header);
        if( !pokemon.empty ) {
            PokemonViewHolder headerHolder = new PokemonViewHolder( header );
            headerHolder.linkTo( header );
            headerHolder.bindTo( pokemon );
        } else {
            header.setVisibility(View.GONE);
        }
    }

    @Override
    public Presenter setupPresenter() {
        if(presenter == null) {
            presenter = new SearchPokemonPresenter(this);
        }
        return presenter;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if( presenter.getDataState() == Presenter.DataState.EMPTY
                || presenter.getDataState() == Presenter.DataState.NOT_FINISHED ) {
            doRequest();
        }
    }

    @Override
    public void doRequest() {
        super.doRequest();
        presenter.fetchPokemonList(new ResponseHandler<ArrayList<Pokemon>>() {
            @Override
            public void onSuccess(Response<ArrayList<Pokemon>> response) {
                adapter.setData( PresentationBuilder.buildPresentation(
                        getActivity(), response.getData() ) );
                presentAdapterData();
                hideLoading();
            }

            @Override
            public void onError(Error error) {
               showError( error );
            }
        });
    }

    @Override
    public void onRetry() {
        doRequest();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.setData( PresentationBuilder.buildPresentation(
                getActivity(), presenter.getPokemonList() ) );
        presentAdapterData();
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        adapter = new PokemonSearchAdapter( );
        adapter.setListener( presenter );
        return adapter;
    }

    @Override
    public void showPokemonDetails(Pokemon item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PokemonDetailsFragment detailsFragment = PokemonDetailsFragment.newInstance( item,
                DetailsUseContext.REPLACE_CONTEXT );
        detailsFragment.show( transaction, POKEMON_DETAILS_DIALOG_TAG );
    }

    @Override
    public Lifecycle screenLifecycle() {
        return getLifecycle();
    }

    private static class PresentationBuilder {
        @NotNull
        public static ArrayList<PokemonPresentation> buildPresentation( Context context,
                ArrayList<Pokemon> pokemonList ) {
            ArrayList<PokemonPresentation> presentations = new ArrayList<>();
            for ( Pokemon pokemon : pokemonList ) {
                presentations.add( PokemonPresentation.Builder.buildPresentation(
                        context, pokemon ) );
            }
            return presentations;
        }
    }
}
