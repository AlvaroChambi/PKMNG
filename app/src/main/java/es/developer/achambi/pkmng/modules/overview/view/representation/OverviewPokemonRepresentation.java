package es.developer.achambi.pkmng.modules.overview.view.representation;

public class OverviewPokemonRepresentation implements OverviewListItemViewRepresentation {
    public final String name;
    public final String image;
    public final String type;
    public final String totalStats;
    public final String hp;
    public final String defense;
    public final String attack;
    public final String spAttack;
    public final String spDefense;
    public final String speed;

    public OverviewPokemonRepresentation(
           String name,
           String image,
           String type,
           String totalStats,
           String hp,
           String defense,
           String attack,
           String spAttack,
           String spDefense,
           String speed) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.totalStats = totalStats;
        this.hp = hp;
        this.defense = defense;
        this.attack = attack;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.POKEMON;
    }
}
