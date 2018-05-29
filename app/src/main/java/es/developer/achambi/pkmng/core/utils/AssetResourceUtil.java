package es.developer.achambi.pkmng.core.utils;

public class AssetResourceUtil {
    private static final String POKEMON_PATH = "pokemon/";
    private static final String ITEMS_PATH = "items/";
    private static final String ASSET_FILE_PATH = "file:///android_asset/";
    private static final String IMAGE_URL_EXTENSION = ".png";

    private static final String IMAGE_MEGA_TOKEN = "mega";
    private static final String IMAGE_ALOLA_TOKEN = "alola";
    private static final String IMAGE_TOTEM_TOKEN = "totem";
    private static final String IMAGE_TOKEN_SEPARATOR = "-";

    public static String buildImageIdentifier( int id, String identifier )
            throws IllegalArgumentException{
        String[] tokens = identifier.split(IMAGE_TOKEN_SEPARATOR);
        String result = String.valueOf( id );

        if( identifier.contains( IMAGE_TOKEN_SEPARATOR + IMAGE_MEGA_TOKEN ) ) {
            result = append( result, tokens[1] );
            if( tokens.length == 3 ) {
                result = append( result, tokens[2] );
            }
            return result;
        } else if( identifier.contains( IMAGE_TOTEM_TOKEN ) &&
                identifier.contains( IMAGE_ALOLA_TOKEN ) ) {
            if(tokens.length != 3) {
                throw new IllegalArgumentException("invalid identifier format: " + identifier);
            }
            result = append( result, tokens[1] );
            return append( result, tokens[2] );
        } else if( identifier.contains( IMAGE_TOTEM_TOKEN ) ) {
            if(tokens.length < 2) {
                throw new IllegalArgumentException("invalid identifier format: " + identifier);
            }
            return append( result, tokens[1] );
        } else if( identifier.contains( IMAGE_ALOLA_TOKEN ) ) {
            if(tokens.length < 2) {
                throw new IllegalArgumentException("invalid identifier format: " + identifier);
            }
            return append( result, tokens[1] );
        } else {
            return result;
        }
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
