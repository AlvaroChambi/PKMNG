package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class OverviewConfigurationPresentationBuilder {
    public OverviewConfigurationPresentation buildPresentation(
            Context context, PokemonConfig configuration ) {
        OverviewConfigurationPresentation configurationRepresentation =
                new OverviewConfigurationPresentation(
                        configuration.getId(),
                        configuration.getName(),
                        configuration.getPokemon().getImageURL(),
                        configuration.getPokemon().getName(),
                        TypesPresentation.Builder.buildPresentation( context,
                                configuration.getPokemon().getType() ),
                        totalStatsAttribute( configuration.getPokemon().getStats(),
                                context.getResources()),
                        formatAbility( configuration.getAbility(), context.getResources() ),
                        formatItem( configuration.getItem(), context.getResources() ),
                        formatNature( configuration.getNature(), context.getResources() )
                );
        return configurationRepresentation;
    }

    private String totalStatsAttribute(StatsSet statsSet, Resources resources) {
        return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
    }



    private String formatNature(Nature nature, Resources resources ) {
        String formatted = resources.getString(R.string.pokemon_item_nature_tag);
        if( nature.getName() != null ) {
            formatted += nature.getName();
        } else {
            formatted += " - ";
        }
        return formatted;
    }

    private String formatAbility(Ability ability, Resources resources ) {
        String formatted = resources.getString(R.string.pokemon_item_ability_tag);
        if( ability.getName() != null ) {
            formatted += ability.getName();
        } else {
            formatted += " - ";
        }
        return formatted;
    }

    private String formatItem(Item item, Resources resources ) {
        String formatted = resources.getString(R.string.pokemon_item_item_tag);
        if( item.getName() != null ) {
            formatted += item.getName();
        } else {
            formatted += " - ";
        }
        return formatted;
    }
}
