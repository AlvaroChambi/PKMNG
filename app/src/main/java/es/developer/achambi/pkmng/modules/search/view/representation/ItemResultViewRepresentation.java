package es.developer.achambi.pkmng.modules.search.view.representation;

public class ItemResultViewRepresentation {
    public final String name;
    public final String imageUrl;
    public final String shortDescription;

    public ItemResultViewRepresentation (
            String name, String imageUrl, String shortDescription ) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.shortDescription = shortDescription;
    }
}
