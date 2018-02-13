package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class StatsPresentation {
    public final String hp;
    public final String attack;
    public final String defense;
    public final String spAttack;
    public final String spDefense;
    public final String speed;

    public StatsPresentation(String hp, String attack, String defense,
                             String spAttack, String spDefense, String speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }

    public static class Builder {
        public static StatsPresentation buildPresentation( Resources resources, StatsSet statsSet ) {
            return new StatsPresentation(
                    standaloneAttribute( resources, Stat.HP, statsSet ),
                    standaloneAttribute( resources, Stat.ATTACK, statsSet ),
                    standaloneAttribute( resources, Stat.DEFENSE, statsSet ),
                    standaloneAttribute( resources, Stat.SP_ATTACK, statsSet ),
                    standaloneAttribute( resources, Stat.SP_DEFENSE, statsSet ),
                    standaloneAttribute( resources, Stat.SPEED, statsSet )
            );
        }

        private static String standaloneAttribute(Resources resources, Stat stat, StatsSet statsSet) {
            switch (stat) {
                case HP:
                    return resources.getString(R.string.pokemon_item_hp_tag) + statsSet.getHP();
                case DEFENSE:
                    return resources.getString(R.string.pokemon_item_defense_tag) + statsSet.getDefense();
                case ATTACK:
                    return resources.getString(R.string.pokemon_item_attack_tag) + statsSet.getAttack();
                case SP_ATTACK:
                    return resources.getString(R.string.pokemon_item_sp_attack_tag)
                            + statsSet.getSpAttack();
                case SP_DEFENSE:
                    return resources.getString(R.string.pokemon_item_sp_defense_tag)
                            + statsSet.getSPDefense();
                case SPEED:
                    return resources.getString(R.string.pokemon_item_speed_tag) + statsSet.getSpeed();
                default:
                    return "";
            }
        }
    }
}
