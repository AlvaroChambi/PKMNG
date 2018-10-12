package es.developer.achambi.pkmng.modules.ui.presentation;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class MoveTypePresentation {
    public final TypePresentation typePresentation;
    public final String effectiveAgainst;

    public MoveTypePresentation( TypePresentation typePresentation,
                                 String effectiveAgainst ) {
        this.typePresentation = typePresentation;
        this.effectiveAgainst = effectiveAgainst;
    }

    public static class Builder {
        @NotNull
        public static MoveTypePresentation buildPresentation( Context context, Type type ) {
            return new MoveTypePresentation(
                    TypePresentation.Builder.build( context, type ),
                    buildEffectiveAgainst( context, type )
            );
        }

        private static String buildEffectiveAgainst( Context context, Type type ) {
            String result = "";
            HashMap<Type, Float> effectiveAgainst = type.effectiveAgainst();
            for( Type currentType : effectiveAgainst.keySet() ) {
                result += TypePresentation.Builder.build( context, currentType )
                        .name.toString().toLowerCase(Locale.getDefault())
                        + " x" + effectiveAgainst.get( currentType ) + " ";
            }
            return context.getResources().getString( R.string.quick_detail_effective_against_tag,
                    result );
        }
    }
}
