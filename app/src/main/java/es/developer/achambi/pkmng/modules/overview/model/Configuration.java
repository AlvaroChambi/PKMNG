package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class Configuration implements Parcelable{
    private Item item;
    private Ability ability;
    private Nature nature;
    private StatsSet statsSet;

    private Move move0;
    private Move move1;
    private Move move2;
    private Move move3;

    public Configuration() {
        statsSet = new StatsSet();
        item = new Item();
        ability = new Ability();
        nature = new Nature();
        move0 = new Move();
        move1 = new Move();
        move2 = new Move();
        move3 = new Move();
    }

    protected Configuration(Parcel in) {
        item = in.readParcelable(Item.class.getClassLoader());
        ability = in.readParcelable(Ability.class.getClassLoader());
        nature = in.readParcelable(Nature.class.getClassLoader());
        statsSet = in.readParcelable(StatsSet.class.getClassLoader());
        move0 = in.readParcelable(Move.class.getClassLoader());
        move1 = in.readParcelable(Move.class.getClassLoader());
        move2 = in.readParcelable(Move.class.getClassLoader());
        move3 = in.readParcelable(Move.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(item, flags);
        dest.writeParcelable(ability, flags);
        dest.writeParcelable(nature, flags);
        dest.writeParcelable(statsSet, flags);
        dest.writeParcelable(move0, flags);
        dest.writeParcelable(move1, flags);
        dest.writeParcelable(move2, flags);
        dest.writeParcelable(move3, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public StatsSet getStatsSet() {
        return statsSet;
    }

    public void setStatsSet(StatsSet statsSet) {
        this.statsSet = statsSet;
    }

    public Move getMove0() {
        return move0;
    }

    public void setMove0(Move move0) {
        this.move0 = move0;
    }

    public Move getMove1() {
        return move1;
    }

    public void setMove1(Move move1) {
        this.move1 = move1;
    }

    public Move getMove2() {
        return move2;
    }

    public void setMove2(Move move2) {
        this.move2 = move2;
    }

    public Move getMove3() {
        return move3;
    }

    public void setMove3(Move move3) {
        this.move3 = move3;
    }
}
