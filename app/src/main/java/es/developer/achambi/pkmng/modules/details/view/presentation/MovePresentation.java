package es.developer.achambi.pkmng.modules.details.view.presentation;

import android.content.Context;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.ui.presentation.CategoryPresentation;
import es.developer.achambi.pkmng.modules.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class MovePresentation {
    public final String name;
    public final String power;
    public final String accuracy;
    public final CategoryPresentation category;
    public final TypePresentation type;
    public final boolean empty;

    public MovePresentation( String name, String power,
                             String accuracy, CategoryPresentation category,
                             TypePresentation type,
                             boolean empty ) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.category = category;
        this.type = type;
        this.empty = empty;
    }

    public static class Builder {
        public static MovePresentation buildPresentation( Context context, Move move ) {
            return new MovePresentation(
                    move.getName(),
                    formatPower( context, move.getPower() ),
                    formatAccuracy( context, move.getAccuracy() ),
                    CategoryPresentation.Builder.buildPresentation( context, move.getCategory() ),
                    TypePresentation.Builder.build( context, move.getType() ),
                    move.getId() == -1
            );
        }

        private static String formatPower( Context context, int power ) {
            return context.getString( R.string.move_power_text_tag ) + " " + power;
        }

        private static String formatAccuracy( Context context, int accuracy ) {
            return context.getString( R.string.move_accuracy_text_tag ) + " " + accuracy;
        }
    }
}
