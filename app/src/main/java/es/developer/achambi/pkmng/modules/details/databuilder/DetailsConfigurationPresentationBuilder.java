package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatsPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;
import es.developer.achambi.pkmng.modules.details.view.representation.DetailsConfigurationPresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class DetailsConfigurationPresentationBuilder {

    public DetailsConfigurationPresentation buildViewRepresentation(Context context,
                                                                    PokemonConfig configuration ) {

        DetailsConfigurationPresentation view = new DetailsConfigurationPresentation(
                configuration.getId(),
                configuration.getName(),
                configuration.getPokemon().getImageURL(),
                configuration.getPokemon().getName(),
                TypesPresentation.Builder.buildPresentation( context,
                        configuration.getPokemon().getType() ),
                formatAbility( configuration.getAbility(), context.getResources() ),
                formatItem( configuration.getItem(), context.getResources() ),
                formatNature( configuration.getNature(), context.getResources() ),
                configuration.getConfiguration().getMove0().getName(),
                configuration.getConfiguration().getMove1().getName(),
                configuration.getConfiguration().getMove2().getName(),
                configuration.getConfiguration().getMove3().getName(),
                StatsPresentation.Builder.buildPresentation( context.getResources(),
                        configuration.getStats() )
        );

        return view;
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
