package es.developer.achambi.pkmng.modules.overview.model;

import android.util.Pair;

public class Pokemon implements BasePokemon {
    public enum Type {
        ELECTRIC,
        EMPTY;
    }

    private String name;
    private Pair<Type, Type> type;
    private StatsSet stats;
    private String imageURL;

    public Pokemon() {
        stats = new StatsSet();
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public StatsSet getStats() {
        return stats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pair<Type, Type> getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = new Pair<>(type, Type.EMPTY);
    }

    public void setType(Type type0, Type type1) {
        this.type = new Pair<>(type0, type1);
    }

    public void setHP(int hp) {
        stats.setHP(hp);
    }

    public void setAttack(int attack) {
       stats.setAttack(attack);
    }

    public void setDefense(int defense) {
        stats.setDefense(defense);
    }

    public void setSpAttack(int spAttack) {
        stats.setSpAttack(spAttack);
    }

    public void setSpDefense(int spDefense) {
        stats.setSpDefense(spDefense);
    }

    public void setSpeed(int speed) {
        stats.setSpeed(speed);
    }

    @Override
    public int getHP() {
        return stats.getHP();
    }

    @Override
    public int getAttack() {
        return stats.getAttack();
    }

    @Override
    public int getDefense() {
        return stats.getDefense();
    }

    @Override
    public int getSpAttack() {
        return stats.getSpAttack();
    }

    @Override
    public int getSPDefense() {
        return stats.getSPDefense();
    }

    @Override
    public int getSpeed() {
        return stats.getSpeed();
    }

    @Override
    public boolean hasConfig() {
        return false;
    }
}
