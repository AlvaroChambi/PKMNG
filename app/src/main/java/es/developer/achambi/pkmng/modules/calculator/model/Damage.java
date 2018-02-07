package es.developer.achambi.pkmng.modules.calculator.model;

import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class Damage {
    private static final float CRITICAL_HIT_MODIFIER = 1.5f;
    private Pair<Float, Float> moveDamage;
    private int hitsToKO;
    private float modifier;
    private String moveName;
    private Move.Category category;
    private Type type;
    private int power;
    private float effectivenessModifier;

    //empty constructor
    public Damage() {
        this.moveName = "";
    }

    public Damage( Move move ) {
        this.moveName = move.getName();
        this.category = move.getCategory();
        this.type = move.getType();
        this.power = move.getPower();
    }

    public Damage( Pair<Float, Float> moveDamage, int hitsToKO, float modifier ) {
        this.modifier = modifier;
        this.moveDamage = moveDamage;
        this.hitsToKO = hitsToKO;
    }

    public float getEffectivenessModifier() {
        return effectivenessModifier;
    }

    public void setEffectivenessModifier(float effectivenessModifier) {
        this.effectivenessModifier = effectivenessModifier;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public Move.Category getCategory() {
        return category;
    }

    public void setCategory(Move.Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
