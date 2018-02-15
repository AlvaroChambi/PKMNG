package es.developer.achambi.pkmng.modules.calculator.view.presentation;

import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class CalculatorPokemonPresentation {
    public final String name;
    public final boolean empty;

    public CalculatorPokemonPresentation( String name, boolean empty ) {
        this.name = name;
        this.empty = empty;
    }

    public static class Builder {
        public static CalculatorPokemonPresentation buildPresentation( PokemonConfig pokemonConfig ) {
            return new CalculatorPokemonPresentation( pokemonConfig.getName(),
                    pokemonConfig.getId() == -1 );
        }
    }
}
