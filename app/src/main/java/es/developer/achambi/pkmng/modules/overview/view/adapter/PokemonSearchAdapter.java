package es.developer.achambi.pkmng.modules.overview.view.adapter;

import android.view.View;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.view.representation.PokemonPresentation;
import es.developer.achambi.pkmng.modules.overview.view.viewholder.PokemonViewHolder;

public class PokemonSearchAdapter extends SearchAdapterDecorator<PokemonPresentation,
        PokemonViewHolder> {

    public PokemonSearchAdapter(ArrayList<PokemonPresentation> data) {
        super(data);
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
        holder.bindTo( pokemon );
    }
}
