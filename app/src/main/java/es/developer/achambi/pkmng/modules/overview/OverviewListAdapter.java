package es.developer.achambi.pkmng.modules.overview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.developer.achambi.pkmng.R;

public class OverviewListAdapter extends RecyclerView.Adapter<OverviewListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView view;

        public ViewHolder(CardView textView) {
            super(textView);
            this.view = textView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 800;
    }
}
