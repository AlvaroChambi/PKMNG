package es.developer.achambi.pkmng.modules.overview.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public class OverviewListAdapter extends RecyclerView.Adapter<OverviewListAdapter.ViewHolder> {
    private List<Pokemon> pokemonList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pokemonName;
        public TextView pokemonType;
        public TextView pokemonHP;
        public TextView pokemonAttack;
        public TextView pokemonDefense;
        public TextView pokemonSpAttack;
        public TextView pokemonSpDefense;
        public TextView pokemonSpeed;

        public ViewHolder(View rootView) {
            super(rootView);
        }
    }

    public OverviewListAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        viewHolder.pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        viewHolder.pokemonType = rootView.findViewById(R.id.pokemon_type_text);
        viewHolder.pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
        viewHolder.pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
        viewHolder.pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
        viewHolder.pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
        viewHolder.pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
        viewHolder.pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonType.setText(pokemon.getType().first.toString().toLowerCase());
        holder.pokemonHP.setText(String.valueOf( pokemon.getHP() ));
        holder.pokemonAttack.setText(String.valueOf( pokemon.getAttack() ));
        holder.pokemonDefense.setText(String.valueOf( pokemon.getDefense() ));
        holder.pokemonSpAttack.setText(String.valueOf( pokemon.getSpAttack() ));
        holder.pokemonSpDefense.setText(String.valueOf( pokemon.getSPDefense() ));
        holder.pokemonSpeed.setText(String.valueOf( pokemon.getSpeed() ));
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
