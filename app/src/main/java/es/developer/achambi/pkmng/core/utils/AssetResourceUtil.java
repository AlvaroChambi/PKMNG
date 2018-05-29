package es.developer.achambi.pkmng.core.utils;

public class AssetResourceUtil {
    private static final String POKEMON_PATH = "pokemon/";
    private static final String ITEMS_PATH = "items/";
    private static final String ASSET_FILE_PATH = "file:///android_asset/";
    private static final String IMAGE_URL_EXTENSION = ".png";

    private static final String IMAGE_TOKEN_SEPARATOR = "-";

    public static String buildImageIdentifier( final String baseUrl, String identifier )
            throws IllegalArgumentException{
        if( baseUrl == null || identifier == null ) {
            return "";
        }
        String[] tokens = identifier.split(IMAGE_TOKEN_SEPARATOR);
        String result = String.copyValueOf( baseUrl.toCharArray() );
        //identifier first token(name) will be avoided
        for( int i = 1; i < tokens.length; i++ ) {
            result = append( result, tokens[i] );
        }
        return result;
    }

    private static String append( String base, String extension ) {
        return base + IMAGE_TOKEN_SEPARATOR + extension;
    }

    public static String buildPokemonImageAssetPath(String imageName) {
        return ASSET_FILE_PATH + POKEMON_PATH + imageName + IMAGE_URL_EXTENSION;
    }

    public static String buildItemImageAssetPath(String name) {
        return ASSET_FILE_PATH + ITEMS_PATH + name + IMAGE_URL_EXTENSION;
    }
}
