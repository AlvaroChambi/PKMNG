package es.developer.achambi.pkmng.modules.overview.view.representation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatsPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;

public class PokemonPresentation implements SearchListData {
    public final int id;
    public final String name;
    public final String image;
    public final TypesPresentation type;
    public final String totalStats;
    public final StatsPresentation stats;

    public PokemonPresentation(
            int id,
           String name,
           String image,
           TypesPresentation type,
           String totalStats,
           StatsPresentation stats ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.totalStats = totalStats;
        this.stats = stats;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_view_id;
    }
}
