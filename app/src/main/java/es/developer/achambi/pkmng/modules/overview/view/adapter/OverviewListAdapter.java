package es.developer.achambi.pkmng.modules.overview.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class OverviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int POKEMON_VIEW_TYPE = 1;
    private static final int CONFIGURATION_VIEW_TYPE = 2;
    private List<BasePokemon> pokemonList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pokemonName;
        public TextView pokemonType;
        public TextView baseStats;

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

    public static class ConfigViewHolder extends RecyclerView.ViewHolder {
        public TextView configName;

        public TextView item;
        public TextView ability;
        public TextView nature;
        public TextView baseStats;

        public TextView pokemonName;
        public TextView pokemonType;
        public TextView pokemonHP;
        public TextView pokemonAttack;
        public TextView pokemonDefense;
        public TextView pokemonSpAttack;
        public TextView pokemonSpDefense;
        public TextView pokemonSpeed;

        public ConfigViewHolder(View rootView) {
            super(rootView);
        }
    }

    public OverviewListAdapter(List<BasePokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = null;
        RecyclerView.ViewHolder holder = null;
        if( viewType == POKEMON_VIEW_TYPE ) {
            rootView =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pokemon_list_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(rootView);
            holder = viewHolder;
            viewHolder.pokemonName = rootView.findViewById(R.id.pokemon_name_text);
            viewHolder.pokemonType = rootView.findViewById(R.id.pokemon_type_text);
            viewHolder.baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

            viewHolder.pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
            viewHolder.pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
            viewHolder.pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
            viewHolder.pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
            viewHolder.pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
            viewHolder.pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);
        } else if( viewType == CONFIGURATION_VIEW_TYPE ) {
            rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pokemon_config_list_item_layout, parent, false);
            ConfigViewHolder viewHolder = new ConfigViewHolder(rootView);
            holder = viewHolder;
            viewHolder.configName = rootView.findViewById(R.id.pokemon_config_name_text);

            viewHolder.pokemonName = rootView.findViewById(R.id.pokemon_name_text);
            viewHolder.pokemonType = rootView.findViewById(R.id.pokemon_type_text);
            viewHolder.baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

            viewHolder.pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
            viewHolder.pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
            viewHolder.pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
            viewHolder.pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
            viewHolder.pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
            viewHolder.pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);

            viewHolder.item = rootView.findViewById(R.id.pokemon_item_text);
            viewHolder.ability = rootView.findViewById(R.id.pokemon_ability_text);
            viewHolder.nature = rootView.findViewById(R.id.pokemon_nature_text);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( getItemViewType(position) == POKEMON_VIEW_TYPE ) {
            Pokemon pokemon = (Pokemon) pokemonList.get(position);
            ((ViewHolder)holder).pokemonName.setText(pokemon.getName());
            ((ViewHolder)holder).pokemonType.setText(pokemon.getType().first.toString().toLowerCase());
            ((ViewHolder)holder).baseStats.setText("BST: 320");
            ((ViewHolder)holder).pokemonHP.setText(String.valueOf( "Hp " + pokemon.getHP() ));
            ((ViewHolder)holder).pokemonAttack.setText(String.valueOf( "Atk " + pokemon.getAttack() ));
            ((ViewHolder)holder).pokemonDefense.setText(String.valueOf( "Def " + pokemon.getDefense() ));
            ((ViewHolder)holder).pokemonSpAttack.setText(String.valueOf( "Spa " + pokemon.getSpAttack() ));
            ((ViewHolder)holder).pokemonSpDefense.setText(String.valueOf( "Spd " + pokemon.getSPDefense() ));
            ((ViewHolder)holder).pokemonSpeed.setText(String.valueOf( "Spe " + pokemon.getSpeed() ));
        } else if( getItemViewType(position) == CONFIGURATION_VIEW_TYPE ) {
            PokemonConfig pokemonConfig = (PokemonConfig) pokemonList.get(position);
            Pokemon pokemon = pokemonConfig.getPokemon();
            ((ConfigViewHolder)holder).configName.setText(pokemonConfig.getName());

            ((ConfigViewHolder)holder).pokemonName.setText(pokemon.getName());
            ((ConfigViewHolder)holder).pokemonType.setText(pokemon.getType().first.toString().toLowerCase());//BAD!!!
            ((ConfigViewHolder)holder).baseStats.setText("BST: 320");
//            ((ConfigViewHolder)holder).pokemonHP.setText(String.valueOf( pokemon.getHP() ));
//            ((ConfigViewHolder)holder).pokemonAttack.setText(String.valueOf( pokemon.getAttack() ));
//            ((ConfigViewHolder)holder).pokemonDefense.setText(String.valueOf( pokemon.getDefense() ));
//            ((ConfigViewHolder)holder).pokemonSpAttack.setText(String.valueOf( pokemon.getSpAttack() ));
//            ((ConfigViewHolder)holder).pokemonSpDefense.setText(String.valueOf( pokemon.getSPDefense() ));
//            ((ConfigViewHolder)holder).pokemonSpeed.setText(String.valueOf( pokemon.getSpeed() ));

            ((ConfigViewHolder)holder).item.setText(String.valueOf( pokemonConfig.getItem() ));
            ((ConfigViewHolder)holder).ability.setText(String.valueOf( pokemonConfig.getAbility() ));
            ((ConfigViewHolder)holder).nature.setText(String.valueOf( pokemonConfig.getNature() ));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return pokemonList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
