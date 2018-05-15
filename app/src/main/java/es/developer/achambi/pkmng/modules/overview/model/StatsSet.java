package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import es.developer.achambi.pkmng.core.utils.ParcelUtil;

public class StatsSet implements Parcelable{
    public static final int MAX_STAT_EV = 252;
    public static final int MAX_TOTAL_EVS = 510;
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

    public StatsSet(HashMap<Stat, Integer> stats) {
        this.stats = stats;
    }

    public StatsSet(StatsSet statsSet ) {
        this( new HashMap<>(statsSet.getStats() ) );
    }

    @Override
    public boolean equals(Object obj) {
        if( this == obj ) {
            return true;
        }

        if( obj == null ) {
            return false;
        }

        if( getClass() != obj.getClass() ) {
            return false;
        }

        StatsSet statsSet = (StatsSet)obj;
        Iterator<Stat> iterator = this.stats.keySet().iterator();
        while( iterator.hasNext() ) {
            Stat currentStat = iterator.next();
            int value = stats.get( currentStat );
            int compareValue = statsSet.getStats().get( currentStat );
            if( value != compareValue ) {
                return false;
            }
        }
        return true;
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

    public HashMap<Stat, Integer> getStats() {
        return stats;
    }

    public Iterator<Stat> getKeysIterator() {
        return stats.keySet().iterator();
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

    public int getTotalStatsPreview( Stat stat, int candidateValue ) {
        return ( getTotalStats() - stats.get( stat ) ) + candidateValue;
    }

    protected StatsSet(Parcel in) {
        stats = ParcelUtil.readParcelableMap(in, Stat.class);
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
        ParcelUtil.writeParcelableMap(parcel, stats);
    }
}
