package es.developer.achambi.pkmng.modules.ui.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Stat;

public class NatureDetailPresentation {
    public final String increased;
    public final String decreased;

    public NatureDetailPresentation( String increased,
                                     String decreased ) {
        this.increased = increased;
        this.decreased = decreased;
    }

    public static class Builder {
        public static NatureDetailPresentation buildPresentation( Context context, Stat increased,
                                                           Stat decreased ) {
            return new NatureDetailPresentation(
                    natureStat( context.getResources(), increased, true ),
                    natureStat( context.getResources(), decreased, false )
            );
        }

        private static String natureStat( Resources resources,
                                   Stat stat, boolean increased ) {
            if( stat == Stat.NONE ) {
                return "";
            }

            if( increased ) {
                String statText = StatPresentation.Builder.buildPresentation( resources, stat ).name;
                return resources.getString( R.string.nature_increased_stat_text, statText );
            } else {
                String statText = StatPresentation.Builder.buildPresentation( resources, stat ).name;
                return resources.getString( R.string.nature_decreased_stat_text, statText );
            }
        }
    }
}
