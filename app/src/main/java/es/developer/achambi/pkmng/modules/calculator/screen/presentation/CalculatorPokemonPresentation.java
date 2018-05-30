package es.developer.achambi.pkmng.modules.calculator.screen.presentation;

import android.content.Context;
import android.net.Uri;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class CalculatorPokemonPresentation {
    public final String name;
    public final boolean empty;
    public final Uri image;

    public CalculatorPokemonPresentation( String name, boolean empty, Uri image ) {
        this.name = name;
        this.empty = empty;
        this.image = image;
    }

    public static class Builder {
        public static CalculatorPokemonPresentation buildPresentation( Context context,
                PokemonConfig pokemonConfig ) {
            return new CalculatorPokemonPresentation(
                    formatName( context, pokemonConfig.getName() ),
                    pokemonConfig.getId() == -1,
                    ImageResourceBuilder.buildPokemonImageAssetPath(
                            pokemonConfig.getPokemon().getBaseImageUrl() ) );
        }

        private static String formatName( Context context, String name ) {
            if( name.isEmpty() ) {
                return context.getString( R.string.text_empty_placeholder );
            } else {
                return name;
            }
        }
    }
}
