package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class NaturePresentation {
    public final String name;
    public final NatureDetailPresentation details;
    public final boolean empty;

    public NaturePresentation( String name, NatureDetailPresentation details,
                               boolean empty ) {
        this.name = name;
        this.details = details;
        this.empty = empty;
    }

    public static class Builder {
        public static NaturePresentation buildPresentation( Context context, Nature nature ) {
            return new NaturePresentation(
                    formatNature( nature, context.getResources() ),
                    NatureDetailPresentation.Builder.buildPresentation( context,
                            nature.getIncreasedStat(), nature.getDecreasedStat() ),
                    nature.getId() == -1
            );
        }

        private static String formatNature( Nature nature, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_nature_tag);
            if( nature.getName() != null && !nature.getName().isEmpty() ) {
                formatted += nature.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }
    }
}
