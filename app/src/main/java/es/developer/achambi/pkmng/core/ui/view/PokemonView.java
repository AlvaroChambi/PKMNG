package es.developer.achambi.pkmng.core.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;

public class PokemonView extends ConstraintLayout {
    public PokemonView(Context context) {
        super(context);
        init( context, null );
    }

    public PokemonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context, attrs );
    }

    public PokemonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context, attrs );
    }

    private void init( Context context, AttributeSet attrs ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pokemon_list_item_layout, this);
        if( attrs != null ) {
            final TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.PokemonView);
            if( typedArray.hasValue(R.styleable.PokemonView_pokemonTextColor) ) {
                int defaultColor = ContextCompat.getColor(
                        context, R.color.secondary_text_default_material_light );
                int color = typedArray.getColor(R.styleable.PokemonView_pokemonTextColor,
                        defaultColor );
                setTextColor( color );
            }

            typedArray.recycle();
        }
    }

    private void setTextColor( int color ) {
        TextView pokemonName = findViewById(R.id.pokemon_name_text);
        TextView baseStats = findViewById(R.id.pokemon_total_base_stats);

        TextView pokemonHP = findViewById(R.id.pokemon_hp_text);
        TextView pokemonAttack = findViewById(R.id.pokemon_atk_text);
        TextView pokemonDefense = findViewById(R.id.pokemon_def_text);
        TextView pokemonSpAttack = findViewById(R.id.pokemon_spa_text);
        TextView pokemonSpDefense = findViewById(R.id.pokemon_spd_text);
        TextView pokemonSpeed = findViewById(R.id.pokemon_speed_text);

        pokemonName.setTextColor( color );
        baseStats.setTextColor( color );
        pokemonHP.setTextColor( color );
        pokemonAttack.setTextColor( color );
        pokemonDefense.setTextColor( color );
        pokemonSpAttack.setTextColor( color );
        pokemonSpDefense.setTextColor( color );
        pokemonSpeed.setTextColor( color );
    }
}
