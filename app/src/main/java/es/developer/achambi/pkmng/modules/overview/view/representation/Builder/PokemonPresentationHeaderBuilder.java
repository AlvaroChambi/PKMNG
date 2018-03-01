package es.developer.achambi.pkmng.modules.overview.view.representation.Builder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;
import es.developer.achambi.pkmng.core.ui.presentation.StatSetPresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.view.representation.PokemonPresentation;

public class PokemonPresentationHeaderBuilder {
    public static PokemonPresentation buildPresentation(
            Context context, Pokemon pokemon ) {
        return new PokemonPresentation(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getImageURL(),
                PokemonTypePresentation.Builder.buildPresentation( context, pokemon.getType() ),
                totalStatsAttribute(context.getResources(), pokemon.getStats()),
                StatSetPresentation.Builder.buildPresentation( context.getResources(),
                        pokemon.getStats() ),
                buildTextColor( context )
        );
    }

    private static String totalStatsAttribute(Resources resources, StatsSet statsSet) {
        return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
    }

    private static int buildTextColor( Context context ) {
        return ContextCompat.getColor( context, R.color.text_primary );
    }
}
