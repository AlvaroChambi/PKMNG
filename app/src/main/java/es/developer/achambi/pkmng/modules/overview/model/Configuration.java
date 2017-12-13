package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Configuration implements Parcelable{
    private String item;
    private String ability;
    private String nature;
    private StatsSet statsSet;

    private String move0;
    private String move1;
    private String move2;
    private String move3;

    public Configuration() {
        statsSet = new StatsSet();
    }

    protected Configuration(Parcel in) {
        item = in.readString();
        ability = in.readString();
        nature = in.readString();
        statsSet = in.readParcelable(StatsSet.class.getClassLoader());

        move0 = in.readString();
        move1 = in.readString();
        move2 = in.readString();
        move3 = in.readString();
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

    public String getMove0() {
        return move0;
    }

    public void setMove0(String move0) {
        this.move0 = move0;
    }

    public String getMove1() {
        return move1;
    }

    public void setMove1(String move1) {
        this.move1 = move1;
    }

    public String getMove2() {
        return move2;
    }

    public void setMove2(String move2) {
        this.move2 = move2;
    }

    public String getMove3() {
        return move3;
    }

    public void setMove3(String move3) {
        this.move3 = move3;
    }

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

        dest.writeString(move0);
        dest.writeString(move1);
        dest.writeString(move2);
        dest.writeString(move3);
    }
}
