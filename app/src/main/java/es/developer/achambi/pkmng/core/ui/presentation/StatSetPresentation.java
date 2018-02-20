package es.developer.achambi.pkmng.core.ui.presentation;

import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class StatSetPresentation {
    public final String hp;
    public final String attack;
    public final String defense;
    public final String spAttack;
    public final String spDefense;
    public final String speed;

    public StatSetPresentation(String hp, String attack, String defense,
                               String spAttack, String spDefense, String speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }

    public static class Builder {
        public static StatSetPresentation buildPresentation(Resources resources, StatsSet statsSet ) {
            return new StatSetPresentation(
                    StatPresentation.Builder.buildPresentation( resources, Stat.HP ).name
                            + ": " + String.valueOf( statsSet.getHP() ),
                    StatPresentation.Builder.buildPresentation( resources, Stat.ATTACK ).name
                            + ": " + String.valueOf( statsSet.getAttack() ),
                    StatPresentation.Builder.buildPresentation( resources, Stat.DEFENSE ).name
                            + ": " + String.valueOf( statsSet.getDefense() ),
                    StatPresentation.Builder.buildPresentation( resources, Stat.SP_ATTACK ).name
                            + ": " + String.valueOf( statsSet.getSpAttack() ),
                    StatPresentation.Builder.buildPresentation( resources, Stat.SP_DEFENSE ).name
                            + ": " + String.valueOf( statsSet.getSPDefense() ),
                    StatPresentation.Builder.buildPresentation( resources, Stat.SPEED ).name
                            + ": " + String.valueOf( statsSet.getSpeed() )
            );
        }
    }
}
