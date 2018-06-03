package es.developer.achambi.pkmng.modules.data.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFormatUtil {
    private static String TYPE_RAW_FORMAT_REGEX = "\\[\\]\\{type:(.[^\\}]*)\\}";
    private static String MECHANIC_RAW_FORMAT_REGEX = "\\[(.[^\\]]*)\\]\\{mechanic:.[^\\}]*\\}";
    private static String POKEMON_RAW_FORMAT_REGEX = "\\[\\]\\{pokemon:(.[^\\}]*)\\}";
    private static String MOVE_EFFECT_CHANCE_REGEX = "$effect_chance%";

    /**
     * Find known tokens( pokemon, mechanic, type, and effect_chance)
     * and simplify it's value in order to make it human readable, on multiple instances of the same
     * token, just the first match will be formatted
     * @param message String to be formatted
     * @return formatted string
     * @throws IllegalArgumentException on a null message
     */
    public String formatDescriptionMessage( String message ) throws IllegalArgumentException {
        if( message == null ) {
            throw new IllegalArgumentException( "invalid message: " + message );
        }
        message = overrideTypeToken( message );
        message = overrideEffectChanceToken( message );
        message = overrideMechanicToken( message );
        message = overridePokemonToken( message );

        return message;
    }


    private String overrideTypeToken( String message ) {
        Matcher tokenMatcher = Pattern.compile( TYPE_RAW_FORMAT_REGEX ).matcher(message);
        if( tokenMatcher.find() ) {
            message = tokenMatcher.replaceAll( tokenMatcher.group( 1 ) );
        }
        return message;
    }

    private String overrideMechanicToken( String message ) {
        Matcher tokenMatcher = Pattern.compile( MECHANIC_RAW_FORMAT_REGEX ).matcher(message);
        if( tokenMatcher.find() ) {
            message = tokenMatcher.replaceFirst( tokenMatcher.group( 1 ) );
        }
        return message;
    }

    private String overridePokemonToken( String message ) {
        Matcher tokenMatcher = Pattern.compile( POKEMON_RAW_FORMAT_REGEX ).matcher( message );
        if( tokenMatcher.find() ) {
            message = tokenMatcher.replaceAll( tokenMatcher.group( 1 ) );
        }
        return message;
    }

    private String overrideEffectChanceToken( String message ) {
        return message.replace( MOVE_EFFECT_CHANCE_REGEX, "" );
    }
}
