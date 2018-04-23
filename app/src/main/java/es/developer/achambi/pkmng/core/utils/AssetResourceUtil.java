package es.developer.achambi.pkmng.core.utils;

public class AssetResourceUtil {
    private static final String ASSET_FILE_PATH = "file:///android_asset/";
    private static final String IMAGE_URL_EXTENSION = ".png";

    public static String buildImageAssetPath(String imageName) {
        return ASSET_FILE_PATH + imageName + IMAGE_URL_EXTENSION;
    }
}
