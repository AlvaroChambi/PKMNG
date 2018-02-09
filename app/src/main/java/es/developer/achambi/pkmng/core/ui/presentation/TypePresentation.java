package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;

import java.util.HashMap;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class TypePresentation {
    public final CharSequence name;
    public final ColorStateList backgroundColor;

    public TypePresentation( CharSequence name, ColorStateList backgroundColor ) {
        this.name = name;
        this.backgroundColor = backgroundColor;
    }

    public static class TypePresentationBuilder {
        public static String buildWeakTo(Context context, Pair<Type, Type> type) {
            String result = "";
            HashMap<Type, Float> weakAgainstList = Type.weakAgainst( type );
            for( Type currentType : weakAgainstList.keySet() ) {
                result += build(context, currentType).name.toString().toLowerCase()
                        + " x" + weakAgainstList.get( currentType ) + " ";
            }
            return result;
        }

        public static String buildEffectiveAgains(Context context, Pair<Type, Type> type) {
            String result = "";
            HashMap<Type, Float> weakAgainstList = Type.weakAgainst( type );
            for( Type currentType : weakAgainstList.keySet() ) {
                result += build(context, currentType).name.toString().toLowerCase()
                        + " x" + weakAgainstList.get( currentType ) + " ";
            }
            return result;
        }

        public static TypePresentation build( Context context, Type type ) {
            CharSequence name = "";
            ColorStateList backgroundColor = null;
            switch (type) {
                case NORMAL:
                    name = context.getResources().getText( R.string.type_normal_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_normal_color);
                    break;
                case FIRE:
                    name = context.getResources().getText( R.string.type_fire_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_fire_color);
                    break;
                case WATER:
                    name = context.getResources().getText( R.string.type_water_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_water_color);
                    break;
                case ELECTRIC:
                    name = context.getResources().getText( R.string.type_electric_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_electric_color);
                    break;
                case GRASS:
                    name = context.getResources().getText( R.string.type_grass_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_grass_color);
                    break;
                case ICE:
                    name = context.getResources().getText( R.string.type_ice_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_ice_color);
                    break;
                case FIGHTING:
                    name = context.getResources().getText( R.string.type_fighting_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_fighting_color);
                    break;
                case POISON:
                    name = context.getResources().getText( R.string.type_poison_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_normal_color);
                    break;
                case GROUND:
                    name = context.getResources().getText( R.string.type_ground_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_ground_color);
                    break;
                case FLYING:
                    name = context.getResources().getText( R.string.type_flying_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_flying_color);
                    break;
                case PSYCHIC:
                    name = context.getResources().getText( R.string.type_psychic_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_psychic_color);
                    break;
                case BUG:
                    name = context.getResources().getText( R.string.type_bug_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_bug_color);
                    break;
                case ROCK:
                    name = context.getResources().getText( R.string.type_rock_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_rock_color);
                    break;
                case GHOST:
                    name = context.getResources().getText( R.string.type_ghost_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_ghost_color);
                    break;
                case DRAGON:
                    name = context.getResources().getText( R.string.type_dragon_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_dragon_color);
                    break;
                case DARK:
                    name = context.getResources().getText( R.string.type_dark_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_dark_color);
                    break;
                case STEEL:
                    name = context.getResources().getText( R.string.type_steel_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_steel_type);
                    break;
                case FAIRY:
                    name = context.getResources().getText( R.string.type_fairy_text);
                    backgroundColor = ContextCompat.getColorStateList(
                            context , R.color.type_fairy_type);
                    break;
                case EMPTY:
                    return null;
            }
            return new TypePresentation( name, backgroundColor );
        }
    }
}
