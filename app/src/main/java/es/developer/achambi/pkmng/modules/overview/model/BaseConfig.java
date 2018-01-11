package es.developer.achambi.pkmng.modules.overview.model;

public abstract class BaseConfig implements BasePokemon{
    protected Pokemon pokemon;

    public BaseConfig( Pokemon basePokemon ) {
        this.pokemon = basePokemon;
    }
}
