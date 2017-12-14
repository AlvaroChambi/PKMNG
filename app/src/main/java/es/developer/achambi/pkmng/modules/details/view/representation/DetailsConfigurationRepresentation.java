package es.developer.achambi.pkmng.modules.details.view.representation;

public class DetailsConfigurationRepresentation {
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final String type;

    public final String ability;
    public final String item;
    public final String nature;

    public final String move0;
    public final String move1;
    public final String move2;
    public final String move3;

    public final String hp;
    public final String defense;
    public final String attack;
    public final String spAttack;
    public final String spDefense;
    public final String speed;

    public DetailsConfigurationRepresentation(
            int id,
            String name, String image, String pokemonName,
            String type, String ability, String item, String nature,
            String move0, String move1, String move2, String move3,
            String hp, String defense, String attack, String spAttack, String spDefense,
            String speed) {
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
        this.hp = hp;
        this.defense = defense;
        this.attack = attack;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }
}
