package es.developer.achambi.pkmng.modules.details.view.representation;

import es.developer.achambi.pkmng.core.ui.presentation.StatsPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;

public class DetailsConfigurationPresentation {
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final PokemonTypePresentation type;

    public final String ability;
    public final String item;
    public final String nature;

    public final String move0;
    public final String move1;
    public final String move2;
    public final String move3;

    public final StatsPresentation stats;

    public DetailsConfigurationPresentation(
            int id,
            String name, String image, String pokemonName,
            PokemonTypePresentation type,
            String ability, String item, String nature,
            String move0, String move1, String move2, String move3,
            StatsPresentation stats ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemonName = pokemonName;
        this.type = type;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
        this.move0 = move0;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.stats = stats;
    }
}
