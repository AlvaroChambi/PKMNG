package es.developer.achambi.pkmng.modules.details.view.presentation;

import android.content.Context;

import es.developer.achambi.pkmng.modules.ui.presentation.AbilityPresentation;
import es.developer.achambi.pkmng.modules.ui.presentation.ItemPresentation;
import es.developer.achambi.pkmng.modules.ui.presentation.NaturePresentation;
import es.developer.achambi.pkmng.modules.ui.presentation.StatSetPresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;

public class ConfigurationDetailsPresentation {
    public final String name;
    public final PokemonPresentation pokemon;
    public final AbilityPresentation ability;
    public final ItemPresentation item;
    public final NaturePresentation nature;

    public final MovePresentation move0;
    public final MovePresentation move1;
    public final MovePresentation move2;
    public final MovePresentation move3;

    public final StatSetPresentation stats;

    public ConfigurationDetailsPresentation(
            String name,
            PokemonPresentation pokemon,
            AbilityPresentation ability,
            ItemPresentation item,
            NaturePresentation nature,
            MovePresentation move0, MovePresentation move1,
            MovePresentation move2, MovePresentation move3,
            StatSetPresentation stats ) {
        this.name = name;
        this.pokemon = pokemon;
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
        public static ConfigurationDetailsPresentation buildPresentation(
                Context context, PokemonConfig configuration ) {

            return new ConfigurationDetailsPresentation(
                    formatName( configuration.getName() ),
                    PokemonPresentation.Builder.buildPresentation(
                            context, configuration.getPokemon() ),
                    AbilityPresentation.Builder.buildPresentation(
                            context, configuration.getAbility() ),
                    ItemPresentation.Builder.buildPresentation( context, configuration.getItem() ),
                    NaturePresentation.Builder.buildPresentation(
                            context, configuration.getNature() ),
                    MovePresentation.Builder.buildPresentation( context,
                            configuration.getConfiguration().getMove0() ),
                    MovePresentation.Builder.buildPresentation( context,
                            configuration.getConfiguration().getMove1() ),
                    MovePresentation.Builder.buildPresentation( context,
                            configuration.getConfiguration().getMove2() ),
                    MovePresentation.Builder.buildPresentation( context,
                            configuration.getConfiguration().getMove3() ),
                    StatSetPresentation.Builder.buildPresentation( context.getResources(),
                            configuration.getStats() )
            );
        }

        private static String formatName( String name ) {
            if( name.isEmpty() ) {
                return " - ";
            } else {
                return name;
            }
        }
    }
}
