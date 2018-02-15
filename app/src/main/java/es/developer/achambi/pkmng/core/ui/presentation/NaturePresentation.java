package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class NaturePresentation {
    public final String name;

    public NaturePresentation(String name) {
        this.name = name;
    }

    public static class Builder {
        public static NaturePresentation buildPresentation( Context context, Nature nature ) {
            return new NaturePresentation(
                    formatNature( nature, context.getResources() )
            );
        }

        private static String formatNature( Nature nature, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_nature_tag);
            if( nature.getName() != null ) {
                formatted += nature.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }
    }
}
