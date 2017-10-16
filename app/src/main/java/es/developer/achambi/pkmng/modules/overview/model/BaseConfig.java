package es.developer.achambi.pkmng.modules.overview.model;

public abstract class BaseConfig implements BasePokemon{
    protected BasePokemon pokemon;

    public BaseConfig( BasePokemon basePokemon ) {
        this.pokemon = basePokemon;
    }
}
