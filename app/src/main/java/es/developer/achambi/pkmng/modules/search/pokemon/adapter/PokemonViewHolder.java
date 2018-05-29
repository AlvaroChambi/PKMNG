package es.developer.achambi.pkmng.modules.search.pokemon.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.screen.TypeView;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;

public class PokemonViewHolder extends RecyclerView.ViewHolder {
    public TextView pokemonName;
    public TypeView pokemonType;
    public TextView baseStats;

    public TextView pokemonHP;
    public TextView pokemonAttack;
    public TextView pokemonDefense;
    public TextView pokemonSpAttack;
    public TextView pokemonSpDefense;
    public TextView pokemonSpeed;

    private ImageView pokemonIcon;
    private TextView pokemonLevel;

    public PokemonViewHolder(View rootView) {
        super(rootView);
    }

    public void linkTo(View rootView ) {
        pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        pokemonType = rootView.findViewById(R.id.pokemon_type_text);
        baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

        pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
        pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
        pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
        pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
        pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
        pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);
        pokemonIcon = rootView.findViewById(R.id.pokemon_image_view);
        pokemonLevel = rootView.findViewById(R.id.pokemon_level_text);
    }

    public void bindTo(PokemonPresentation pokemon, RequestManager requestManager) {
        pokemonName.setText(pokemon.name);
        pokemonType.setType(pokemon.type);
        baseStats.setText(pokemon.totalStats);
        pokemonHP.setText(pokemon.stats.hp);
        pokemonAttack.setText(pokemon.stats.attack);
        pokemonDefense.setText(pokemon.stats.defense);
        pokemonSpAttack.setText(pokemon.stats.spAttack);
        pokemonSpDefense.setText(pokemon.stats.spDefense);
        pokemonSpeed.setText(pokemon.stats.speed);
        if( pokemon.image != null && !pokemon.image.isEmpty() ) {
            requestManager
                    .load(Uri.parse(pokemon.image))
                    .into(pokemonIcon);
        }
        pokemonLevel.setText(pokemon.level);
    }
}
