package es.developer.achambi.pkmng.modules.create.view;

public class MoveConfigurationRepresentation {
    public final int id;
    public final String name;
    public final String type;
    public final String power;

    public MoveConfigurationRepresentation(int id, String name, String type, String power) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
    }
}
