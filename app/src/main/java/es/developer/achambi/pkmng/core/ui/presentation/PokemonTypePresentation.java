package es.developer.achambi.pkmng.core.ui.presentation;


import android.content.Context;
import android.support.v4.util.Pair;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class PokemonTypePresentation {
    public final String resistantTo;
    public final String weakAgainst;
    public final TypePresentation first;
    public final TypePresentation second;

    public PokemonTypePresentation(TypePresentation first, TypePresentation second,
                                   String resistantTo, String weakAgainst ) {
        this.first = first;
        this.second = second;
        this.resistantTo = resistantTo;
        this.weakAgainst = weakAgainst;
    }

    public static class Builder {
        @NotNull
        public static PokemonTypePresentation buildPresentation(Context context,
                                                                Pair<Type, Type> type ) {
            return new PokemonTypePresentation(
                    TypePresentation.Builder.build( context, type.first ),
                    TypePresentation.Builder.build( context, type.second ),
                    buildResistantTo( context, type ),
                    buildWeakTo( context, type )
            );
        }

        private static String buildWeakTo(Context context, Pair<Type, Type> type) {
            String result = "";
            HashMap<Type, Float> weakAgainstList = Type.weakAgainst( type );
            for( Type currentType : weakAgainstList.keySet() ) {
                result += TypePresentation.Builder
                        .build(context, currentType).name.toString().toLowerCase()
                        + " x" + weakAgainstList.get( currentType ) + " ";
            }
            return context.getResources().getString( R.string.quick_detail_weak_against_tag,
                    result );
        }

        private static String buildResistantTo(Context context, Pair<Type, Type> type) {
            String result = "";
            HashMap<Type, Float> resistantTo = Type.resistantAgainst( type );
            for( Type currentType : resistantTo.keySet() ) {
                result += TypePresentation.Builder
                        .build(context, currentType).name.toString().toLowerCase()
                        + " x" + resistantTo.get( currentType ) + " ";
            }
            return context.getResources().getString( R.string.quick_detail_resistant_to_tag,
                    result );
        }
    }
}
