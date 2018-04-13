package es.developer.achambi.pkmng.core.ui.screen;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;

public class ConfigurationView extends ConstraintLayout {
    public ConfigurationView(Context context) {
        super(context);
        init( context, null );
    }

    public ConfigurationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context, attrs );
    }

    public ConfigurationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context, attrs );
    }

    private void init( Context context, AttributeSet attrs ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pokemon_config_list_item_layout, this);
        if( attrs != null ) {
            final TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.ConfigurationView);
            if( typedArray.hasValue(R.styleable.ConfigurationView_configTextColor) ) {
                int defaultColor = ContextCompat.getColor(
                        context, R.color.secondary_text_default_material_light );
                int color = typedArray.getColor(R.styleable.ConfigurationView_configTextColor,
                        defaultColor );
                setTextColor( color );
            }

            typedArray.recycle();
        }
    }
    
    private void setTextColor( int color ) {
        TextView configName = findViewById(R.id.pokemon_config_name_text);

        TextView pokemonName = findViewById(R.id.pokemon_name_text);
        TextView baseStats = findViewById(R.id.pokemon_total_base_stats);

        configName.setTextColor( color );
        pokemonName.setTextColor( color );
        baseStats.setTextColor( color );
    }
}
