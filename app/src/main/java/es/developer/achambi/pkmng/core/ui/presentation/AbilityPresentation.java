package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.Context;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public class AbilityPresentation {
    public final String name;
    public final String description;
    public final boolean empty;

    public AbilityPresentation( String name,
                                String description, boolean empty ) {
        this.name = name;
        this.description = description;
        this.empty = empty;
    }

    public static class Builder {
        public static AbilityPresentation buildPresentation( Context context, Ability ability ) {
            return new AbilityPresentation(
                formatAbility( context, ability ),
                ability.getDescriptionShort(),
                ability.getId() == -1
            );
        }

        private static String formatAbility( Context context,  Ability ability ) {
            String formatted = context.getString(R.string.pokemon_item_ability_tag);
            if( ability.getName() != null && !ability.getName().isEmpty() ) {
                formatted += "  " + ability.getName();
            } else {
                formatted += "   - ";
            }
            return formatted;
        }
    }
}
