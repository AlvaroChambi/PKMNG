package es.developer.achambi.pkmng.modules.overview.view.representation;

public class OverviewConfigurationRepresentation implements OverviewListItemViewRepresentation{
    public final String name;
    public final String image;
    public final String pokemonName;
    public final String type;
    public final String totalStats;
    public final String ability;
    public final String item;
    public final String nature;

    public OverviewConfigurationRepresentation(
            String name,
            String image,
            String pokemonName,
            String type,
            String totalStats,
            String ability,
            String item,
            String nature
            ) {
        this.name = name;
        this.image = image;
        this.pokemonName = pokemonName;
        this.type = type;
        this.totalStats = totalStats;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.POKEMON_CONFIG;
    }
}
