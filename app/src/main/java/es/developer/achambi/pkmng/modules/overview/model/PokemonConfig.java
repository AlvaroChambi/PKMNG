package es.developer.achambi.pkmng.modules.overview.model;

public class PokemonConfig extends BaseConfig{

    public PokemonConfig(BasePokemon basePokemon) {
        super(basePokemon);
    }

    @Override
    public int getHP() {
        return pokemon.getHP() + stats.getHP();
    }

    @Override
    public int getAttack() {
        return pokemon.getAttack() + stats.getAttack();
    }

    @Override
    public int getDefense() {
        return pokemon.getDefense() + stats.getDefense();
    }

    @Override
    public int getSpAttack() {
        return pokemon.getSpAttack() + stats.getSpAttack();
    }

    @Override
    public int getSPDefense() {
        return pokemon.getSPDefense() + stats.getSPDefense();
    }

    @Override
    public int getSpeed() {
        return pokemon.getSpeed() + stats.getSpeed();
    }
}
