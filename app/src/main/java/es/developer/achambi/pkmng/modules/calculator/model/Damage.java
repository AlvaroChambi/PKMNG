package es.developer.achambi.pkmng.modules.calculator.model;

import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class Damage {
    private Pair<Float, Float> moveDamage;
    private int hitsToKO;
    private float modifier;
    private String moveName;
    private Move.Category category;
    private Type type;
    private int power;
    private float effectivenessModifier;

    public Damage() {
        this.moveName = "";
    }

    public Damage( Move move ) {
        this.moveName = move.getName();
        this.category = move.getCategory();
        this.type = move.getType();
        this.power = move.getPower();
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

    public Move.Category getCategory() {
        return category;
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

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }
}
