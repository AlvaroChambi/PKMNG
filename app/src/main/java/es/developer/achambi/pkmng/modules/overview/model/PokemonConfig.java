package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;

import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.utils.PokemonUtils;

public class PokemonConfig extends BaseConfig{
    private Configuration configuration;
    private int id;
    private String name;

    public PokemonConfig() {
        super(new Pokemon());
        this.id = EMPTY_ID;
        this.name = "";
        this.configuration = new Configuration();
    }

    protected PokemonConfig(Parcel in) {
        super( (Pokemon) (in.readParcelable(Pokemon.class.getClassLoader())) );
        configuration = in.readParcelable(Configuration.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
    }

    /**
     *
     * @param obj Pokemon configuration to compare
     * @return true if the obj id is the same as the actual configuration id, this will not compare
     * anything but that, for a more detailed compare use Pokemon equals() and Configuration equals()
     */
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
        PokemonConfig pokemonConfig = (PokemonConfig)obj;
        if( pokemonConfig.getId() == this.getId() ) {
            return true;
        }

        return false;
    }

    public static final Creator<PokemonConfig> CREATOR = new Creator<PokemonConfig>() {
        @Override
        public PokemonConfig createFromParcel(Parcel in) {
            return new PokemonConfig(in);
        }

        @Override
        public PokemonConfig[] newArray(int size) {
            return new PokemonConfig[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setPokemon(Pokemon pokemon ) {
        this.pokemon = pokemon;
    }

    public void setConfiguration( Configuration configuration ) {
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getItem() {
        return configuration.getItem();
    }

    public Ability getAbility() {
        return configuration.getAbility();
    }

    public Nature getNature() {
        return configuration.getNature();
    }

    public StatsSet getStats() {
        StatsSet totalStats = new StatsSet();
        totalStats.setHP(getHP());
        totalStats.setAttack(getAttack());
        totalStats.setDefense(getDefense());
        totalStats.setSpAttack(getSpAttack());
        totalStats.setSpDefense(getSPDefense());
        totalStats.setSpeed(getSpeed());
        return totalStats;
    }

    public StatsSet getStatsSet() {
        return configuration.getEvsSet();
    }

    @Override
    public int getHP() {
        return PokemonUtils.getHpStatValue( pokemon.getHP(),
                configuration.getEvsSet().getHP(),
                pokemon.getLevel() );
    }

    @Override
    public int getAttack() {
        return PokemonUtils.getStatValue( pokemon.getAttack(),
                configuration.getEvsSet().getAttack(),
                configuration.getNature().getStatModifier(Stat.ATTACK), pokemon.getLevel() );
    }

    @Override
    public int getDefense() {
        return PokemonUtils.getStatValue( pokemon.getDefense(),
                configuration.getEvsSet().getDefense(),
                configuration.getNature().getStatModifier(Stat.DEFENSE), pokemon.getLevel() );
    }

    @Override
    public int getSpAttack() {
        return PokemonUtils.getStatValue( pokemon.getSpAttack(),
                configuration.getEvsSet().getSpAttack(),
                configuration.getNature().getStatModifier(Stat.SP_ATTACK), pokemon.getLevel() );
    }

    @Override
    public int getSPDefense() {
        return PokemonUtils.getStatValue( pokemon.getSPDefense(),
                configuration.getEvsSet().getSPDefense(),
                configuration.getNature().getStatModifier(Stat.SP_DEFENSE), pokemon.getLevel() );
    }

    @Override
    public int getSpeed() {
        return PokemonUtils.getStatValue( pokemon.getSpeed(),
                configuration.getEvsSet().getSpeed(),
                configuration.getNature().getStatModifier(Stat.SPEED), pokemon.getLevel() );
    }

    @Override
    public int getId() {
        return id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pokemon, flags);
        dest.writeParcelable(configuration, flags);
        dest.writeInt(id);
        dest.writeString(name);
    }
}
