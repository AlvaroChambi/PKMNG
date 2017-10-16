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

public class OverviewViewDataBuilder {
    private Resources resources;

    public List<OverviewListItemViewRepresentation> buildViewRepresentation(
        Resources resources,
        List<BasePokemon> pokemonList ) {

        this.resources = resources;
        List<OverviewListItemViewRepresentation> viewRepresentationList = new ArrayList<>();

        for( BasePokemon pokemon : pokemonList ) {
            if( pokemon.hasConfig() ) {
                viewRepresentationList.add( configurationItemView( (PokemonConfig) pokemon ) );
            } else {
                viewRepresentationList.add( pokemonItemView( (Pokemon) pokemon ) );
            }
        }

        return viewRepresentationList;
    }

    private OverviewPokemonRepresentation pokemonItemView( Pokemon pokemon ) {
        OverviewPokemonRepresentation viewRepresentation = new OverviewPokemonRepresentation(
                pokemon.getName(),
                pokemon.getImageURL(),
                typeAttribute(pokemon.getType()),
                totalStatsAttribute(pokemon.getStats()),
                standaloneAttribute(Stat.HP, pokemon.getStats()),
                standaloneAttribute(Stat.ATTACK, pokemon.getStats()),
                standaloneAttribute(Stat.DEFENSE, pokemon.getStats()),
                standaloneAttribute(Stat.SP_ATTACK, pokemon.getStats()),
                standaloneAttribute(Stat.SP_DEFENSE, pokemon.getStats()),
                standaloneAttribute(Stat.SPEED, pokemon.getStats())
        );
        return viewRepresentation;
    }

    private String totalStatsAttribute(StatsSet statsSet) {
        return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
    }

    private String standaloneAttribute(Stat stat, StatsSet statsSet) {
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

    private String typeAttribute(Pair<Pokemon.Type, Pokemon.Type> type){
        return formatType(type.first) + formatType(type.second);
    }

    private String formatType(Pokemon.Type type) {
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

    private OverviewConfigurationRepresentation configurationItemView( PokemonConfig configuration ) {
        OverviewConfigurationRepresentation configurationRepresentation =
                new OverviewConfigurationRepresentation(
                        configuration.getName(),
                        configuration.getPokemon().getImageURL(),
                        configuration.getPokemon().getName(),
                        typeAttribute(configuration.getPokemon().getType()),
                        totalStatsAttribute(configuration.getPokemon().getStats()),
                        resources.getString(R.string.pokemon_item_ability_tag)
                                + configuration.getAbility(),
                        resources.getString(R.string.pokemon_item_item_tag)
                                + configuration.getItem(),
                        resources.getString(R.string.pokemon_item_nature_tag)
                                + configuration.getNature()
                );
        return configurationRepresentation;
    }
}
