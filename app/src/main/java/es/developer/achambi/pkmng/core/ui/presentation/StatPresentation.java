package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Stat;

public class StatPresentation {
    public final String name;

    public StatPresentation(String name) {
        this.name = name;
    }

    public static class Builder {
        public static StatPresentation buildPresentation( Resources resources, Stat stat ) {
                return new StatPresentation(
                        standaloneAttribute( resources, stat )
                );
        }

        private static String standaloneAttribute( Resources resources, Stat stat ) {
            switch (stat) {
                case HP:
                    return resources.getString(R.string.pokemon_item_hp_tag);
                case DEFENSE:
                    return resources.getString(R.string.pokemon_item_defense_tag);
                case ATTACK:
                    return resources.getString(R.string.pokemon_item_attack_tag);
                case SP_ATTACK:
                    return resources.getString(R.string.pokemon_item_sp_attack_tag);
                case SP_DEFENSE:
                    return resources.getString(R.string.pokemon_item_sp_defense_tag);
                case SPEED:
                    return resources.getString(R.string.pokemon_item_speed_tag);
                default:
                    return "";
            }
        }
    }
}
