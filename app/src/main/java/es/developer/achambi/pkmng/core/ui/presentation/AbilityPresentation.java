package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public class AbilityPresentation {
    public final String name;

    public AbilityPresentation(String name) {
        this.name = name;
    }

    public static class Builder {
        public static AbilityPresentation buildPresentation( Context context, Ability ability ) {
            return new AbilityPresentation(
                formatAbility( context, ability )
            );
        }

        private static String formatAbility( Context context,  Ability ability ) {
            String formatted = context.getString(R.string.pokemon_item_ability_tag);
            if( ability.getName() != null ) {
                formatted += ability.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }
    }
}
