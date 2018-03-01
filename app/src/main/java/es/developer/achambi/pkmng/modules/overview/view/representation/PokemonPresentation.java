package es.developer.achambi.pkmng.modules.overview.view.representation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatSetPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;

public class PokemonPresentation implements SearchListData {
    public final int id;
    public final String name;
    public final String image;
    public final PokemonTypePresentation type;
    public final String totalStats;
    public final StatSetPresentation stats;
    public final int textColor;

    public PokemonPresentation(
            int id,
           String name,
           String image,
           PokemonTypePresentation type,
           String totalStats,
           StatSetPresentation stats,
           int textColor ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.totalStats = totalStats;
        this.stats = stats;
        this.textColor = textColor;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_view_id;
    }
}
