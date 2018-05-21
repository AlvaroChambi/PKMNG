package es.developer.achambi.pkmng.modules.search.nature.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.developer.achambi.pkmng.core.utils.ParcelUtil;
import es.developer.achambi.pkmng.modules.overview.model.Stat;

public class Nature implements Parcelable{
    public static final float INCREASED_STAT_MODIFIER = 0.9f;
    public static final float DECREASED_STAT_MODIFIER = 1.1f;
    public static final float NEUTRAL_STAT_MODIFIER = 1.0f;

    private int id;
    private String name;
    private Stat increasedStat;
    private Stat decreasedStat;

    public Nature() {
        id = -1;
        name = "";
        increasedStat = Stat.NONE;
        decreasedStat = Stat.NONE;
    }

    public Nature(int id, String name, Stat increasedStat, Stat decreasedStat) {
        this.id = id;
        this.name = name;
        this.increasedStat = increasedStat;
        this.decreasedStat = decreasedStat;
    }

    public Nature( Nature nature ) {
        this( nature.getId(), nature.getName(), nature.getIncreasedStat(), nature.getDecreasedStat() );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nature)) return false;
        Nature nature = (Nature) o;
        return id == nature.id &&
                name.equals(nature.name) &&
                increasedStat == nature.increasedStat &&
                decreasedStat == nature.decreasedStat;
    }

    protected Nature(Parcel in) {
        id = in.readInt();
        name = in.readString();
        increasedStat = ParcelUtil.readEnumFromParcel(in, Stat.class);
        decreasedStat = ParcelUtil.readEnumFromParcel(in, Stat.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stat getIncreasedStat() {
        return increasedStat;
    }

    public void setIncreasedStat(Stat increasedStat) {
        this.increasedStat = increasedStat;
    }

    public Stat getDecreasedStat() {
        return decreasedStat;
    }

    public void setDecreasedStat(Stat decreasedStat) {
        this.decreasedStat = decreasedStat;
    }

    public float getStatModifier( Stat stat ) {
        if( stat.equals( increasedStat ) ) {
            return INCREASED_STAT_MODIFIER;
        } else if( stat.equals( decreasedStat ) ) {
            return DECREASED_STAT_MODIFIER;
        } else {
            return NEUTRAL_STAT_MODIFIER;
        }
    }

    public static final Creator<Nature> CREATOR = new Creator<Nature>() {
        @Override
        public Nature createFromParcel(Parcel in) {
            return new Nature(in);
        }

        @Override
        public Nature[] newArray(int size) {
            return new Nature[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        ParcelUtil.writeEnumToParcel(dest, increasedStat);
        ParcelUtil.writeEnumToParcel(dest, decreasedStat);
    }
}
