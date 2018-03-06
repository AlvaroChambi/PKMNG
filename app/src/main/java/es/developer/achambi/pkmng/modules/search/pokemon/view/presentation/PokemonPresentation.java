package es.developer.achambi.pkmng.modules.search.pokemon.view.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatSetPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;

public class PokemonPresentation implements SearchListData {
    public final int id;
    public final String name;
    public final String image;
    public final PokemonTypePresentation type;
    public final String totalStats;
    public final StatSetPresentation stats;
    public final boolean empty;

    public PokemonPresentation(
            int id,
           String name,
           String image,
           PokemonTypePresentation type,
           String totalStats,
           StatSetPresentation stats,
           boolean empty ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.totalStats = totalStats;
        this.stats = stats;
        this.empty = empty;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_view_id;
    }

    public static class Builder {
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
                    pokemon.getId() == -1
            );
        }

        private static String totalStatsAttribute(Resources resources, StatsSet statsSet) {
            return resources.getString(R.string.pokemon_item_total_stats_tag) +
                    statsSet.getTotalStats();
        }
    }
}
