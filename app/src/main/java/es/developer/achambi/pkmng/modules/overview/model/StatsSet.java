package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import es.developer.achambi.pkmng.core.ui.ParcelUtil;

public class StatsSet implements Parcelable{
    private HashMap<Stat, Integer> stats;
    
    public StatsSet() {
        stats = new HashMap<>();
        stats.put(Stat.HP, 0);
        stats.put(Stat.ATTACK, 0);
        stats.put(Stat.DEFENSE, 0);
        stats.put(Stat.SP_ATTACK, 0);
        stats.put(Stat.SP_DEFENSE, 0);
        stats.put(Stat.SPEED, 0);
    }

    public void setHP(int hp) {
        stats.put(Stat.HP, hp);
    }

    public void setAttack(int attack) {
        stats.put(Stat.ATTACK, attack);
    }

    public void setDefense(int defense) {
        stats.put(Stat.DEFENSE, defense);
    }

    public void setSpAttack(int spAttack) {
        stats.put(Stat.SP_ATTACK, spAttack);
    }

    public void setSpDefense(int spDefense) {
        stats.put(Stat.SP_DEFENSE, spDefense);
    }

    public void setSpeed(int speed) {
        stats.put(Stat.SPEED, speed);
    }

    public int getHP() {
        return stats.get(Stat.HP);
    }

    public int getAttack() {
        return stats.get(Stat.ATTACK);
    }

    public int getDefense() {
        return stats.get(Stat.DEFENSE);
    }

    public int getSpAttack() {
        return stats.get(Stat.SP_ATTACK);
    }

    public int getSPDefense() {
        return stats.get(Stat.SP_DEFENSE);
    }

    public int getSpeed() {
        return stats.get(Stat.SPEED);
    }

    public int getTotalStats() {
        int result = 0;
        result += stats.get(Stat.HP);
        result += stats.get(Stat.ATTACK);
        result += stats.get(Stat.DEFENSE);
        result += stats.get(Stat.SP_ATTACK);
        result += stats.get(Stat.SP_DEFENSE);
        result += stats.get(Stat.SPEED);

        return result;
    }

    protected StatsSet(Parcel in) {
        stats = ParcelUtil.readParcelableMap(in, Stat.class, Integer.class);
    }

    public static final Creator<StatsSet> CREATOR = new Creator<StatsSet>() {
        @Override
        public StatsSet createFromParcel(Parcel in) {
            return new StatsSet(in);
        }

        @Override
        public StatsSet[] newArray(int size) {
            return new StatsSet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
