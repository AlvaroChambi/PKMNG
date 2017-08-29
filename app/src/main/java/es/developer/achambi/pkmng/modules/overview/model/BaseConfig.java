package es.developer.achambi.pkmng.modules.overview.model;

public abstract class BaseConfig implements BasePokemon{
    protected BasePokemon pokemon;
    protected StatsSet stats;

    public BaseConfig( BasePokemon basePokemon ) {
        this.pokemon = basePokemon;
        stats = new StatsSet();
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
}
