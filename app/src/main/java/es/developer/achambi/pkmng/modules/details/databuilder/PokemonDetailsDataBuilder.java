package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.res.Resources;
import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

public class PokemonDetailsDataBuilder {
    private Resources resources;

    public OverviewPokemonRepresentation buildViewRepresentation(
            Resources resources, Pokemon pokemon ) {
        this.resources = resources;

        OverviewPokemonRepresentation viewRepresentation = new OverviewPokemonRepresentation(
                pokemon.getId(),
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

    private String typeAttribute(Pair<Type, Type> type){
        return formatType(type.first) + formatType(type.second);
    }

    private String formatType(Type type) {
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
}
