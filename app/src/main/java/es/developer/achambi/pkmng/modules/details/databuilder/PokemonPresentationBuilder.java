package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatsPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.view.representation.PokemonPresentation;

public class PokemonPresentationBuilder {
    public PokemonPresentation buildPresentation(
            Context context, Pokemon pokemon ) {
        return new PokemonPresentation(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getImageURL(),
                PokemonTypePresentation.Builder.buildPresentation( context, pokemon.getType() ),
                totalStatsAttribute(context.getResources(), pokemon.getStats()),
                StatsPresentation.Builder.buildPresentation( context.getResources(),
                        pokemon.getStats() )
        );
    }

    private String totalStatsAttribute( Resources resources, StatsSet statsSet) {
        return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
    }
}
