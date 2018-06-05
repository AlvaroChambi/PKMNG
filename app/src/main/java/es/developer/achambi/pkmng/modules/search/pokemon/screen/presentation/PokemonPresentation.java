package es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatSetPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;
import es.developer.achambi.pkmng.core.utils.ImageResourceBuilder;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;

public class PokemonPresentation implements SearchListData {
    public final int id;
    public final String name;
    public final Uri image;
    public final int defaultImage;
    public final PokemonTypePresentation type;
    public final String totalStats;
    public final StatSetPresentation stats;
    public final String level;
    public final boolean empty;

    public PokemonPresentation(
            int id,
           String name,
           Uri image,
           int defaultImage,
           PokemonTypePresentation type,
           String totalStats,
           StatSetPresentation stats,
           String level,
           boolean empty ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.defaultImage = defaultImage;
        this.type = type;
        this.totalStats = totalStats;
        this.stats = stats;
        this.level = level;
        this.empty = empty;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_view_id;
    }

    @Override
    public int getId() {
        return id;
    }

    public static class Builder {
        public static PokemonPresentation buildPresentation(
                Context context, Pokemon pokemon ) {
            return new PokemonPresentation(
                    pokemon.getId(),
                    pokemon.getName(),
                    ImageResourceBuilder.buildPokemonImageAssetPath( pokemon.getBaseImageUrl() ),
                    R.drawable.icon_placeholder,
                    PokemonTypePresentation.Builder.buildPresentation( context, pokemon.getType() ),
                    totalStatsAttribute(context.getResources(), pokemon.getStats()),
                    StatSetPresentation.Builder.buildPresentation( context.getResources(),
                            pokemon.getStats() ),
                    buildLevelString( context.getResources(), pokemon.getLevel() ),
                    pokemon.getId() == -1
            );
        }

        private static String buildLevelString( Resources resources, int level ) {
            return resources.getString(R.string.pokemon_level_tag) + " " +level;
        }

        private static String totalStatsAttribute(Resources resources, StatsSet statsSet) {
            return resources.getString(R.string.pokemon_item_total_stats_tag) +
                    statsSet.getTotalStats();
        }
    }
}
