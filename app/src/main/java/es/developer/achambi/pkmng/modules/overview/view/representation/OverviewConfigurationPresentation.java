package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class OverviewConfigurationPresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final PokemonTypePresentation type;
    public final String totalStats;
    public final String ability;
    public final String item;
    public final String nature;

    public OverviewConfigurationPresentation(
            int id,
            String name,
            String image,
            String pokemonName,
            PokemonTypePresentation type,
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

    public static class Builder {
        public static OverviewConfigurationPresentation buildPresentation(
                Context context, PokemonConfig configuration ) {
            OverviewConfigurationPresentation configurationRepresentation =
                    new OverviewConfigurationPresentation(
                            configuration.getId(),
                            configuration.getName(),
                            configuration.getPokemon().getImageURL(),
                            configuration.getPokemon().getName(),
                            PokemonTypePresentation.Builder.buildPresentation( context,
                                    configuration.getPokemon().getType() ),
                            totalStatsAttribute( configuration.getPokemon().getStats(),
                                    context.getResources()),
                            formatAbility( configuration.getAbility(), context.getResources() ),
                            formatItem( configuration.getItem(), context.getResources() ),
                            formatNature( configuration.getNature(), context.getResources() )
                    );
            return configurationRepresentation;
        }

        private static String totalStatsAttribute(StatsSet statsSet, Resources resources) {
            return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
        }

        private static String formatNature(Nature nature, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_nature_tag);
            if( nature.getName() != null ) {
                formatted += nature.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }

        private static String formatAbility(Ability ability, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_ability_tag);
            if( ability.getName() != null ) {
                formatted += ability.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }

        private static String formatItem(Item item, Resources resources ) {
            String formatted = resources.getString(R.string.pokemon_item_item_tag);
            if( item.getName() != null ) {
                formatted += item.getName();
            } else {
                formatted += " - ";
            }
            return formatted;
        }
    }
}
