package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemPresentation {
    public final String name;

    public ItemPresentation(String name) {
        this.name = name;
    }

    public static class Builder {
        public static ItemPresentation buildPresentation( Context context, Item item ) {
            return new ItemPresentation(
                formatItem( item, context.getResources() )
            );
        }

        private static String formatItem( Item item, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_item_tag);
            if( item.getName() != null ) {
                formatted += item.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }
    }
}
