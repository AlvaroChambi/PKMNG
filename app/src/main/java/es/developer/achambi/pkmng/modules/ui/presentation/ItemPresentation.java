package es.developer.achambi.pkmng.modules.ui.presentation;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemPresentation {
    public final String name;
    public final Uri image;
    public final String description;
    public final boolean empty;

    public ItemPresentation(String name, Uri image, String description, boolean empty) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.empty = empty;
    }

    public static class Builder {
        public static ItemPresentation buildPresentation( Context context, Item item ) {
            return new ItemPresentation(
                formatItem( item, context.getResources() ),
                ImageResourceBuilder.buildItemImageAssetPath(item.getName()),
                item.getDescriptionShort(),
                item.getId() == -1
            );
        }

        private static String formatItem( Item item, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_item_tag);
            if( item.getName() != null && !item.getName().isEmpty() ) {
                formatted += item.getName();
            } else {
                formatted += "  - ";
            }
            return formatted;
        }
    }
}
