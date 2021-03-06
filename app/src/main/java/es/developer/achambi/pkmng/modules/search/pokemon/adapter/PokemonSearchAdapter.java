package es.developer.achambi.pkmng.modules.search.pokemon.adapter;

import android.view.View;

import com.bumptech.glide.RequestManager;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.ui.SearchAdapterDecorator;
import es.developer.achambi.coreframework.utils.GlideRequests;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;

public class PokemonSearchAdapter extends SearchAdapterDecorator<PokemonPresentation,
        PokemonViewHolder> {
    private GlideRequests requestManager;
    public PokemonSearchAdapter(GlideRequests requestManager) {
        super();
        this.requestManager = requestManager;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.pokemon_list_item_cardview_layout;
    }

    @Override
    public int getAdapterViewType() {
        return R.id.pokemon_view_id;
    }

    @Override
    public PokemonViewHolder createViewHolder(View rootView ) {
        PokemonViewHolder viewHolder = new PokemonViewHolder(rootView);
        viewHolder.linkTo( rootView );
        return viewHolder;
    }

    @Override
    public void bindViewHolder( PokemonViewHolder holder,
                                PokemonPresentation pokemon ) {
        holder.bindTo( pokemon, requestManager );
    }
}
