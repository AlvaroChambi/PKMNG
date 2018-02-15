package es.developer.achambi.pkmng.modules.details.view.representation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.StatsPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class DetailsConfigurationPresentation {
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final PokemonTypePresentation type;

    public final String ability;
    public final String item;
    public final String nature;

    public final String move0;
    public final String move1;
    public final String move2;
    public final String move3;

    public final StatsPresentation stats;

    public DetailsConfigurationPresentation(
            int id,
            String name, String image, String pokemonName,
            PokemonTypePresentation type,
            String ability, String item, String nature,
            String move0, String move1, String move2, String move3,
            StatsPresentation stats ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemonName = pokemonName;
        this.type = type;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
        this.move0 = move0;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.stats = stats;
    }

    public static class Builder {
        public static DetailsConfigurationPresentation buildPresentation(Context context,
                                                                        PokemonConfig configuration ) {

            DetailsConfigurationPresentation view = new DetailsConfigurationPresentation(
                    configuration.getId(),
                    configuration.getName(),
                    configuration.getPokemon().getImageURL(),
                    configuration.getPokemon().getName(),
                    PokemonTypePresentation.Builder.buildPresentation( context,
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
