package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Configuration implements Parcelable{
    private String item;
    private String ability;
    private String nature;
    private StatsSet statsSet;

    public Configuration() {
        statsSet = new StatsSet();
    }

    protected Configuration(Parcel in) {
        item = in.readString();
        ability = in.readString();
        nature = in.readString();
        statsSet = in.readParcelable(StatsSet.class.getClassLoader());
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel in) {
            return new Configuration(in);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public StatsSet getStatsSet() {
        return statsSet;
    }

    public void setStatsSet(StatsSet statsSet) {
        this.statsSet = statsSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeString(ability);
        dest.writeString(nature);
        dest.writeParcelable(statsSet, flags);
    }
}
