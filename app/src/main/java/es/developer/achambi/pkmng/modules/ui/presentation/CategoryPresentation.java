package es.developer.achambi.pkmng.modules.ui.presentation;

import android.content.Context;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class CategoryPresentation {
    public final String name;

    private CategoryPresentation(String name) {
        this.name = name;
    }

    public static class Builder {
        public static CategoryPresentation buildPresentation(
                Context context, Move.Category category ) {
            return new CategoryPresentation(
                formatCategory( context, category )
            );
        }

        private static String formatCategory( Context context, Move.Category category ) {
            switch (category) {
                case SPECIAL:
                    return context.getString( R.string.move_category_special_tag );
                case PHYSICAL:
                    return context.getString( R.string.move_category_physical_tag );
                case NON_DAMAGING:
                    return context.getString( R.string.name_category_non_damaging_tag );
            }
            return "";
        }
    }
}
