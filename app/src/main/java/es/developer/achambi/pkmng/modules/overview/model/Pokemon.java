package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.util.Pair;

import es.developer.achambi.pkmng.core.utils.ParcelUtil;

public class Pokemon implements BasePokemon{
    public enum Type {
        ELECTRIC,
        GROUND,
        EMPTY;
    }

    private final int id;
    private String name;
    private Pair<Type, Type> type;
    private StatsSet stats;
    private String imageURL;

    public Pokemon( int id  ) {
        this.id = id;
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
    public int getId() {
        return id;
    }

    protected Pokemon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = ParcelUtil.readParcelablePair(in, Type.class, Type.class);
        stats = in.readParcelable(StatsSet.class.getClassLoader());
        imageURL = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
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
        ParcelUtil.writeParcelablePair(dest, type);
        dest.writeParcelable(stats, flags);
        dest.writeString(imageURL);
    }

}
