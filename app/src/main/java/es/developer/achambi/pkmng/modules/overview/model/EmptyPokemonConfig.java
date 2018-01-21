package es.developer.achambi.pkmng.modules.overview.model;

public class EmptyPokemonConfig extends PokemonConfig {

    public EmptyPokemonConfig() {
        super( -1, new EmptyPokemon(), new Configuration() );
    }
}
