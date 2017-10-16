package es.developer.achambi.pkmng.modules.overview.view.representation;

public interface OverviewListItemViewRepresentation {
    enum ViewType {
        POKEMON,
        POKEMON_CONFIG,
    }
    ViewType getViewType();
}
