package es.developer.achambi.pkmng.modules.search.view.representation;

public class ItemResultViewRepresentation {
    public final int id;
    public final String name;
    public final String imageUrl;
    public final String description;

    public ItemResultViewRepresentation (
            int id,
            String name, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
