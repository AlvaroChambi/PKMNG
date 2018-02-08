package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;

public class OverviewConfigurationRepresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final Pair<TypePresentation, TypePresentation> type;
    public final String totalStats;
    public final String ability;
    public final String item;
    public final String nature;

    public OverviewConfigurationRepresentation(
            int id,
            String name,
            String image,
            String pokemonName,
            Pair<TypePresentation, TypePresentation> type,
            String totalStats,
            String ability,
            String item,
            String nature
            ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemonName = pokemonName;
        this.type = type;
        this.totalStats = totalStats;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_configuration_view_id;
    }
}
