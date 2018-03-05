package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.AbilityPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.ItemPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.NaturePresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class OverviewConfigurationPresentation implements SearchListData {
    public final int id;
    public final String name;
    public final PokemonPresentation pokemon;
    public final String totalStats;
    public final AbilityPresentation ability;
    public final ItemPresentation item;
    public final NaturePresentation nature;
    public final boolean empty;

    public OverviewConfigurationPresentation(
            int id,
            String name,
            PokemonPresentation pokemon,
            String totalStats,
            AbilityPresentation ability,
            ItemPresentation item,
            NaturePresentation nature,
            boolean empty ) {
        this.id = id;
        this.name = name;
        this.pokemon = pokemon;
        this.totalStats = totalStats;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
        this.empty = empty;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_configuration_view_id;
    }

    public static class Builder {
        public static OverviewConfigurationPresentation buildPresentation(
                Context context, PokemonConfig configuration ) {
            OverviewConfigurationPresentation configurationRepresentation =
                    new OverviewConfigurationPresentation(
                            configuration.getId(),
                            formatName( configuration.getName() ),
                            PokemonPresentation.Builder.buildPresentation( context,
                                    configuration.getPokemon() ),
                            totalStatsAttribute( configuration.getPokemon().getStats(),
                                    context.getResources()),
                            AbilityPresentation.Builder.buildPresentation(
                                    context, configuration.getAbility() ),
                            ItemPresentation.Builder.buildPresentation(
                                    context, configuration.getItem() ),
                            NaturePresentation.Builder.buildPresentation(
                                    context, configuration.getNature() ),
                            configuration.getId() == -1
                    );
            return configurationRepresentation;
        }

        private static String formatName( String name ) {
            if( name.isEmpty() ) {
                return " - ";
            } else {
                return name;
            }
        }

        private static String totalStatsAttribute(StatsSet statsSet, Resources resources) {
            return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
        }
    }
}
