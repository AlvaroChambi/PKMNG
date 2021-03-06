package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;
import android.support.v4.util.Pair;


import es.developer.achambi.coreframework.utils.ParcelUtil;

public class Pokemon implements BasePokemon{
    private static final int FIXED_LEVEL = 50;

    private final int id;
    private String name;
    private Pair<Type, Type> type;
    private StatsSet stats;
    private String baseImageUrl;
    private int level;

    public Pokemon() {
        this.id = EMPTY_ID;
        this.level = FIXED_LEVEL;
        stats = new StatsSet();
        type = new Pair<>(Type.EMPTY, Type.EMPTY);
    }

    public Pokemon( int id  ) {
        this.id = id;
        this.level = FIXED_LEVEL;
        stats = new StatsSet();
        type = new Pair<>(Type.EMPTY, Type.EMPTY);
    }

    public Pokemon( Pokemon pokemon ) {
        this.id = pokemon.getId();
        this.name = pokemon.getName();
        this.type = new Pair<>(pokemon.getType().first, pokemon.getType().second);
        this.stats = new StatsSet( pokemon.getStats() );
        this.baseImageUrl = pokemon.getBaseImageUrl();
        this.level = pokemon.getLevel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pokemon)) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id;
    }

    public int getLevel() {
        return level;
    }

    public void setStats(StatsSet stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
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

    public void setType( Pair<Type, Type> type ) {
        this.type = type;
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
        baseImageUrl = in.readString();
        level = in.readInt();
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
        dest.writeString(baseImageUrl);
        dest.writeInt(level);
    }

}
