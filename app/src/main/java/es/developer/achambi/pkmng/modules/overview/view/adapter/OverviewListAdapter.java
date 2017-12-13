package es.developer.achambi.pkmng.modules.overview.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewListItemViewRepresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

public class OverviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OverviewListItemViewRepresentation> pokemonList;
    private OnItemClickedListener listener;

    public interface OnItemClickedListener {
        void onPokemonClicked( OverviewPokemonRepresentation pokemonRepresentation );
        void onConfigurationClicked( OverviewConfigurationRepresentation pokemonRepresentation );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public OverviewPokemonRepresentation pokemon;
        private OnItemClickedListener listener;

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
        public void bindListener( final OnItemClickedListener listener,
                                  final OverviewPokemonRepresentation pokemon ) {
            this.pokemon = pokemon;
            this.listener = listener;
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewHolder.this.listener.onPokemonClicked( ViewHolder.this.pokemon );
                }
            });
        }
    }

    public static class ConfigViewHolder extends RecyclerView.ViewHolder {
        public OverviewConfigurationRepresentation configuration;
        private OnItemClickedListener listener;

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

        public void bindListener( final OnItemClickedListener listener,
                                  final OverviewConfigurationRepresentation configuration ) {
            this.configuration = configuration;
            this.listener = listener;
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConfigViewHolder.this.listener.onConfigurationClicked(
                            ConfigViewHolder.this.configuration );
                }
            });
        }
    }

    public OverviewListAdapter(List<OverviewListItemViewRepresentation> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public void setOnItemClickedListener( OnItemClickedListener listener ) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        if( viewType == OverviewListItemViewRepresentation.ViewType.POKEMON.ordinal() ) {
            View rootView =  LayoutInflater.from(parent.getContext())
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
        } else if( viewType == OverviewListItemViewRepresentation.ViewType.POKEMON_CONFIG.ordinal() ) {
            View rootView = LayoutInflater.from(parent.getContext())
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
        if( getItemViewType(position) == OverviewListItemViewRepresentation.ViewType
                .POKEMON.ordinal() ) {
            OverviewPokemonRepresentation pokemon =
                    (OverviewPokemonRepresentation) pokemonList.get(position);
            if( listener != null ) {
                ((ViewHolder)holder).bindListener(listener, pokemon);
            }

            ((ViewHolder)holder).pokemonName.setText(pokemon.name);
            ((ViewHolder)holder).pokemonType.setText(pokemon.type);
            ((ViewHolder)holder).baseStats.setText(pokemon.totalStats);
            ((ViewHolder)holder).pokemonHP.setText(pokemon.hp);
            ((ViewHolder)holder).pokemonAttack.setText(pokemon.attack);
            ((ViewHolder)holder).pokemonDefense.setText(pokemon.defense);
            ((ViewHolder)holder).pokemonSpAttack.setText(pokemon.spAttack);
            ((ViewHolder)holder).pokemonSpDefense.setText(pokemon.spDefense);
            ((ViewHolder)holder).pokemonSpeed.setText(pokemon.speed);
        } else if( getItemViewType(position) == OverviewListItemViewRepresentation.ViewType
                .POKEMON_CONFIG.ordinal() ) {
            OverviewConfigurationRepresentation configuration =
                    (OverviewConfigurationRepresentation) pokemonList.get(position);
            if( listener != null ) {
                ((ConfigViewHolder)holder).bindListener(listener, configuration);
            }

            ((ConfigViewHolder)holder).configName.setText(configuration.name);

            ((ConfigViewHolder)holder).pokemonName.setText(configuration.pokemonName);
            ((ConfigViewHolder)holder).pokemonType.setText(configuration.type);
            ((ConfigViewHolder)holder).baseStats.setText(configuration.totalStats);
            ((ConfigViewHolder)holder).item.setText(configuration.item);
            ((ConfigViewHolder)holder).ability.setText(configuration.ability);
            ((ConfigViewHolder)holder).nature.setText(configuration.nature);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return pokemonList.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
