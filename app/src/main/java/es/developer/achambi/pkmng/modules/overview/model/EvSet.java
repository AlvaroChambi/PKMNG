package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EvSet implements Parcelable {
    private static final int NO_ID = -1;
    private Integer id;
    private StatsSet statsSet;

    public EvSet() {
        this.id = NO_ID;
        statsSet = new StatsSet();
    }

    public EvSet( EvSet evSet ) {
        this.id = evSet.id;
        this.statsSet = new StatsSet(evSet.statsSet);
    }

    public StatsSet getStats() {
        return statsSet;
    }

    public void setStats(StatsSet statsSet) {
        this.statsSet = statsSet;
    }


    protected EvSet(Parcel in) {
        id = in.readInt();
        statsSet = in.readParcelable(StatsSet.class.getClassLoader());
    }

    public static final Creator<EvSet> CREATOR = new Creator<EvSet>() {
        @Override
        public EvSet createFromParcel(Parcel in) {
            return new EvSet(in);
        }

        @Override
        public EvSet[] newArray(int size) {
            return new EvSet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(statsSet, flags);
    }
}
