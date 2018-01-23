package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.content.res.Resources;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class OverviewViewDataBuilder {

    public ArrayList<OverviewPokemonRepresentation> buildPokemonPresentation(Resources resources,
                                                                             List<Pokemon> pokemonList ) {
        ArrayList<OverviewPokemonRepresentation> viewRepresentationList = new ArrayList<>();
        for( Pokemon pokemon : pokemonList ) {
            viewRepresentationList.add( pokemonItemView( pokemon, resources ) );
        }
        return viewRepresentationList;
    }

    public ArrayList<OverviewConfigurationRepresentation> buildConfigurationPresentation(
            Resources resources, List<PokemonConfig> configList ) {
        ArrayList<OverviewConfigurationRepresentation> viewRepresentationList = new ArrayList<>();
        for( PokemonConfig config : configList ) {
            viewRepresentationList.add( configurationItemView(config, resources) );
        }
        return viewRepresentationList;
    }

    private OverviewPokemonRepresentation pokemonItemView( Pokemon pokemon, Resources resources ) {
        OverviewPokemonRepresentation viewRepresentation = new OverviewPokemonRepresentation(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getImageURL(),
                typeAttribute(pokemon.getType(), resources),
                totalStatsAttribute(pokemon.getStats(), resources),
                standaloneAttribute(Stat.HP, pokemon.getStats(), resources),
                standaloneAttribute(Stat.ATTACK, pokemon.getStats(), resources),
                standaloneAttribute(Stat.DEFENSE, pokemon.getStats(), resources),
                standaloneAttribute(Stat.SP_ATTACK, pokemon.getStats(), resources),
                standaloneAttribute(Stat.SP_DEFENSE, pokemon.getStats(), resources),
                standaloneAttribute(Stat.SPEED, pokemon.getStats(), resources)
        );
        return viewRepresentation;
    }

    private String totalStatsAttribute(StatsSet statsSet, Resources resources) {
        return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
    }

    private String standaloneAttribute(Stat stat, StatsSet statsSet, Resources resources) {
        switch (stat) {
            case HP:
                return resources.getString(R.string.pokemon_item_hp_tag) + statsSet.getHP();
            case DEFENSE:
                return resources.getString(R.string.pokemon_item_attack_tag) + statsSet.getDefense();
            case ATTACK:
                return resources.getString(R.string.pokemon_item_defense_tag) + statsSet.getAttack();
            case SP_ATTACK:
                return resources.getString(R.string.pokemon_item_sp_attack_tag)
                        + statsSet.getSpAttack();
            case SP_DEFENSE:
                return resources.getString(R.string.pokemon_item_sp_defense_tag)
                        + statsSet.getSPDefense();
            case SPEED:
                return resources.getString(R.string.pokemon_item_speed_tag) + statsSet.getSpeed();
            default:
                return "";
        }
    }

    private String typeAttribute(Pair<Type, Type> type, Resources resources){
        return formatType(type.first, resources) + formatType(type.second, resources);
    }

    private String formatType(Type type, Resources resources) {
        String formattedType = "";
        switch (type) {
            case ELECTRIC:
                formattedType = resources.getString(R.string.pokemon_type_electric);
                break;
            case EMPTY:
                break;
        }

        return formattedType;
    }

    public OverviewConfigurationRepresentation configurationItemView( PokemonConfig configuration,
                                                                      Resources resources ) {
        OverviewConfigurationRepresentation configurationRepresentation =
                new OverviewConfigurationRepresentation(
                        configuration.getId(),
                        configuration.getName(),
                        configuration.getPokemon().getImageURL(),
                        configuration.getPokemon().getName(),
                        typeAttribute(configuration.getPokemon().getType(), resources),
                        totalStatsAttribute(configuration.getPokemon().getStats(), resources),
                        formatAbility( configuration.getAbility(), resources ),
                        formatItem( configuration.getItem(), resources ),
                        formatNature( configuration.getNature(), resources )
                );
        return configurationRepresentation;
    }

    private String formatNature( Nature nature, Resources resources ) {
        String formatted = resources.getString(R.string.pokemon_item_nature_tag);
        if( nature.getName() != null ) {
            formatted += nature.getName();
        } else {
            formatted += " - ";
        }
        return formatted;
    }

    private String formatAbility( Ability ability, Resources resources ) {
        String formatted = resources.getString(R.string.pokemon_item_ability_tag);
        if( ability.getName() != null ) {
            formatted += ability.getName();
        } else {
            formatted += " - ";
        }
        return formatted;
    }

    private String formatItem( Item item, Resources resources ) {
        String formatted = resources.getString(R.string.pokemon_item_item_tag);
        if( item.getName() != null ) {
            formatted += item.getName();
        } else {
            formatted += " - ";
        }
        return formatted;
    }
}
