package es.developer.achambi.pkmng.core.utils;

public class AssetResourceUtil {
    private static final String POKEMON_PATH = "pokemon/";
    private static final String ITEMS_PATH = "items/";
    private static final String ASSET_FILE_PATH = "file:///android_asset/";
    private static final String IMAGE_URL_EXTENSION = ".png";

    public static String buildPokemonImageAssetPath(String imageName) {
        return ASSET_FILE_PATH + POKEMON_PATH + imageName + IMAGE_URL_EXTENSION;
    }

    public static String buildItemImageAssetPath(String name) {
        return ASSET_FILE_PATH + ITEMS_PATH + name + IMAGE_URL_EXTENSION;
    }
}
