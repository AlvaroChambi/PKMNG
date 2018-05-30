package es.developer.achambi.pkmng.core.utils;

import android.net.Uri;

public class ImageResourceBuilder {
    private static final String POKEMON_PATH = "pokemon/";
    private static final String ITEMS_PATH = "items/";
    private static final String ASSET_FILE_PATH = "file:///android_asset/";
    private static final String IMAGE_URL_EXTENSION = ".png";

    private static final String IMAGE_TOKEN_SEPARATOR = "-";

    public String buildPokemonImageIdentifier( int speciesId, String identifier )
            throws IllegalArgumentException {
        if( identifier == null ) {
            throw new IllegalArgumentException( "found invalid identifier: " + identifier );
        }

        String[] tokens = identifier.split(IMAGE_TOKEN_SEPARATOR);
        String result = String.valueOf( speciesId );
        //identifier's first token(name) will be avoided
        for( int i = 1; i < tokens.length; i++ ) {
            result = append( result, tokens[i] );
        }
        return result;
    }

    private String append( String base, String extension ) {
        return base + IMAGE_TOKEN_SEPARATOR + extension;
    }

    public static Uri buildPokemonImageAssetPath(String imageName) {
        return Uri.parse( ASSET_FILE_PATH + POKEMON_PATH + imageName + IMAGE_URL_EXTENSION );
    }

    public static Uri buildItemImageAssetPath(String name) {
        return Uri.parse( ASSET_FILE_PATH + ITEMS_PATH + name + IMAGE_URL_EXTENSION );
    }
}
