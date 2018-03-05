package es.developer.achambi.pkmng.modules.search.pokemon;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.details.view.PokemonDetailsFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.view.OverviewFragment;
import es.developer.achambi.pkmng.modules.overview.view.SearchPokemonView;
import es.developer.achambi.pkmng.modules.overview.view.adapter.PokemonSearchAdapter;
import es.developer.achambi.pkmng.modules.overview.view.representation.PokemonPresentation;
import es.developer.achambi.pkmng.modules.overview.view.viewholder.PokemonViewHolder;
import es.developer.achambi.pkmng.modules.search.pokemon.presenter.SearchPokemonPresenter;

public class SearchPokemonFragment extends BaseSearchListFragment implements SearchPokemonView {
    private static final String POKEMON_DETAILS_DIALOG_TAG = "POKEMON_DETAILS_DIALOG_TAG";
    private static final String CURRENT_POKEMON_ARGUMENT_KEY = "CURRENT_POKEMON_ARGUMENT_KEY";

    private SearchPokemonPresenter presenter;
    private ArrayList<PokemonPresentation> pokemonList;
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
    public ViewPresenter setupPresenter() {
        if(presenter == null) {
            presenter = new SearchPokemonPresenter(this);
        }
        return presenter;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
    }

    @Override
    public void doRequest() {
        pokemonList = PresentationBuilder.buildPresentation(
                getActivity(), presenter.fetchPokemonList() );
        refreshAdapter();
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        return new PokemonSearchAdapter( pokemonList );
    }

    @Override
    public void showPokemonDetails(Pokemon item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PokemonDetailsFragment detailsFragment = PokemonDetailsFragment.newInstance( item,
                        OverviewFragment.UseContext.REPLACE_SEARCH_CONTEXT );
        detailsFragment.show( transaction, POKEMON_DETAILS_DIALOG_TAG );
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
