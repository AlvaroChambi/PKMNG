package es.developer.achambi.pkmng.modules.create.view;

public class MoveConfigurationRepresentation {
    public final int id;
    public final String name;
    public final String type;
    public final String power;
    public final boolean isEmpty;

    public MoveConfigurationRepresentation(int id, String name, String type, String power,
                                           boolean isEmpty) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
        this.isEmpty = isEmpty;
    }
}


