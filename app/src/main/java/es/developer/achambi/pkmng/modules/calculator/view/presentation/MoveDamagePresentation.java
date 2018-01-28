package es.developer.achambi.pkmng.modules.calculator.view.presentation;

public class MoveDamagePresentation {
    public final String name;
    public final String type;
    public final String category;
    public final String power;
    public final String effect;
    public final String result;
    public final boolean empty;

    public MoveDamagePresentation(String name,
                                  String type, String category, String power,
                                  String effect, String result, boolean empty) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.effect = effect;
        this.result = result;
        this.empty = empty;
    }
}
