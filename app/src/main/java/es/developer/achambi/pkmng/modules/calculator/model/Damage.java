package es.developer.achambi.pkmng.modules.calculator.model;

import android.util.Pair;

public class Damage {
    private static final float CRITICAL_HIT_MODIFIER = 1.5f;
    private Pair<Float, Float> moveDamage;
    private int hitsToKO;
    private float modifier;

    public Damage( Pair<Float, Float> moveDamage, int hitsToKO, float modifier ) {
        this.modifier = modifier;
        this.moveDamage = moveDamage;
        this.hitsToKO = hitsToKO;
    }

    public Pair<Float, Float> getMoveDamage() {
        return moveDamage;
    }

    public void setMoveDamage(Pair<Float, Float> moveDamage) {
        this.moveDamage = moveDamage;
    }

    public int getHitsToKO() {
        return hitsToKO;
    }

    public void setHitsToKO(int hitsToKO) {
        this.hitsToKO = hitsToKO;
    }

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }
}
