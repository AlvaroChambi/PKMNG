package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.os.Parcelable;

public interface OverviewListItemViewRepresentation extends Parcelable{
    enum ViewType {
        POKEMON,
        POKEMON_CONFIG,
    }
    ViewType getViewType();
}
