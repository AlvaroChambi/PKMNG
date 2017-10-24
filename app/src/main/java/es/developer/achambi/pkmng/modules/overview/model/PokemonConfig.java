package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcel;

public class PokemonConfig extends BaseConfig{
    private Configuration configuration;

    public PokemonConfig(BasePokemon basePokemon, Configuration configuration) {
        super(basePokemon);
        this.configuration = configuration;
    }

    protected PokemonConfig(Parcel in) {
        super( (BasePokemon) (in.readParcelable(BasePokemon.class.getClassLoader())) );
        configuration = in.readParcelable(Configuration.class.getClassLoader());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getItem() {
        return configuration.getItem();
    }

    public String getAbility() {
        return configuration.getAbility();
    }

    public String getNature() {
        return configuration.getNature();
    }

    @Override
    public int getHP() {
        return pokemon.getHP() + configuration.getStatsSet().getHP();
    }

    @Override
    public int getAttack() {
        return pokemon.getAttack() + configuration.getStatsSet().getAttack();
    }

    @Override
    public int getDefense() {
        return pokemon.getDefense() + configuration.getStatsSet().getDefense();
    }

    @Override
    public int getSpAttack() {
        return pokemon.getSpAttack() + configuration.getStatsSet().getSpAttack();
    }

    @Override
    public int getSPDefense() {
        return pokemon.getSPDefense() + configuration.getStatsSet().getSPDefense();
    }

    @Override
    public int getSpeed() {
        return pokemon.getSpeed() + configuration.getStatsSet().getSpeed();
    }

    @Override
    public boolean hasConfig() {
        return true;
    }

    public Pokemon getPokemon() {
        return (Pokemon) pokemon;
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

    }
}
