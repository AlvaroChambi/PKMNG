package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;

import java.util.HashMap;

public class EvSet extends StatsSet {
    private static final int NO_ID = -1;
    private int id;

    public EvSet() {
        this.id = NO_ID;
    }

    public EvSet(HashMap<Stat, Integer> stats) {
        super(stats);
    }

    public EvSet(StatsSet statsSet) {
        super(statsSet);
    }

    public EvSet(Parcel in) {
        super(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
