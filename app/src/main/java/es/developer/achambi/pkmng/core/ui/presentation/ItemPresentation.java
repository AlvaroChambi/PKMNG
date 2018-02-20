package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class ItemPresentation {
    public final String name;
    public final String description;
    public final boolean empty;

    public ItemPresentation(String name, String description, boolean empty) {
        this.name = name;
        this.description = description;
        this.empty = empty;
    }

    public static class Builder {
        public static ItemPresentation buildPresentation( Context context, Item item ) {
            return new ItemPresentation(
                formatItem( item, context.getResources() ),
                item.getDescriptionShort(),
                item.getId() == -1
            );
        }

        private static String formatItem( Item item, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_item_tag);
            if( item.getName() != null && !item.getName().isEmpty() ) {
                formatted += item.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }
    }
}
