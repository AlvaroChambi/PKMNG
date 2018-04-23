package es.developer.achambi.pkmng.modules.search.configuration.screen.presentation;

import android.content.Context;
import android.content.res.Resources;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.AbilityPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.ItemPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.NaturePresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;
import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;

public class ConfigurationPresentation implements SearchListData {
    public final int id;
    public final String name;
    public final PokemonPresentation pokemon;
    public final String totalStats;
    public final AbilityPresentation ability;
    public final ItemPresentation item;
    public final NaturePresentation nature;
    public final boolean empty;

    public ConfigurationPresentation(
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

    /**
     * Equals case used on adapter implementations to check if this item has been modified
     */
    @Override
    public boolean equals(Object obj) {
        if( this == obj ) {
            return true;
        }

        if( obj == null ) {
            return false;
        }

        if( getClass() != obj.getClass() ) {
            return false;
        }
        ConfigurationPresentation presentation = (ConfigurationPresentation)obj;
        return ( this.id == presentation.id
                && this.name.equals( presentation.name )
                && this.pokemon.id == presentation.pokemon.id
                && this.totalStats.equals( presentation.totalStats )
                && this.ability.name.equals( presentation.ability.name )
                && this.item.name.equals( presentation.item.name )
                && this.nature.name.equals( presentation.nature.name ));
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_configuration_view_id;
    }

    @Override
    public int getId() {
        return id;
    }

    public static class Builder {
        public static ConfigurationPresentation buildPresentation(
                Context context, PokemonConfig configuration ) {
            return new ConfigurationPresentation(
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
