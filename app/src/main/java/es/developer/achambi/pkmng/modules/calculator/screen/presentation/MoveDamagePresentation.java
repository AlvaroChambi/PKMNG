package es.developer.achambi.pkmng.modules.calculator.screen.presentation;

import android.content.Context;
import android.support.v4.util.Pair;

import java.math.BigDecimal;

import es.developer.achambi.pkmng.core.ui.presentation.CategoryPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.MoveTypePresentation;
import es.developer.achambi.pkmng.modules.calculator.model.Damage;

public class MoveDamagePresentation {
    public final String name;
    public final MoveTypePresentation type;
    public final CategoryPresentation category;
    public final String power;
    public final String effect;
    public final String result;
    public final boolean empty;

    public MoveDamagePresentation(String name,
                                  MoveTypePresentation type,
                                  CategoryPresentation category, String power,
                                  String effect, String result, boolean empty) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.effect = effect;
        this.result = result;
        this.empty = empty;
    }

    public static class Builder {
        public static MoveDamagePresentation buildPresentation( Context context, Damage damage ) {
            boolean empty = damage.getMoveName().equals("");
            if(empty) {
                return buildEmpty();
            } else {
                return new MoveDamagePresentation(
                        damage.getMoveName(),
                        MoveTypePresentation.Builder.buildPresentation(
                                context, damage.getType() ),
                        CategoryPresentation.Builder.buildPresentation(
                                context, damage.getCategory()),
                        "Power " + damage.getPower(),
                        buildEffectivenessText( damage.getEffectivenessModifier() ),
                        formatDamage( damage ),
                        damage.getMoveName().equals("") );
            }
        }

        private static String formatDamage( Damage damage ) {
            Pair<Float, Float> damageResult = damage.getMoveDamage();
            return "Guaranteed " + damage.getHitsToKO() + "HKO  " +
                    round( damageResult.first, 2 ) + " ~ " +
                    round( damageResult.second, 2 );
        }

        private static float round(float d, int decimalPlace) {
            BigDecimal bd = new BigDecimal(Float.toString(d));
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
            return bd.floatValue();
        }

        private static String buildEffectivenessText( float modifier ) {
            String result = "";
            if( modifier >= 2 ) {
                result = "SuperEffective: x" + modifier;
            } else if( modifier == 1 ) {
                result = "Effective: x" + modifier;
            } else if ( modifier < 1 ) {
                result = "Not Effective: x" + modifier;
            }
            return result;
        }

        private static MoveDamagePresentation buildEmpty() {
            return new MoveDamagePresentation("", null, null, "", "", "", true);
        }
    }
}
