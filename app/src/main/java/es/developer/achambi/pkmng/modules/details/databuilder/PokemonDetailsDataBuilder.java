package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

public class PokemonDetailsDataBuilder {
    public OverviewPokemonRepresentation buildViewRepresentation(
            Context context, Pokemon pokemon ) {

        OverviewPokemonRepresentation viewRepresentation = new OverviewPokemonRepresentation(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getImageURL(),
                typeAttribute( context, pokemon.getType() ),
                totalStatsAttribute(context.getResources(), pokemon.getStats()),
                standaloneAttribute(context.getResources(), Stat.HP, pokemon.getStats()),
                standaloneAttribute(context.getResources(), Stat.ATTACK, pokemon.getStats()),
                standaloneAttribute(context.getResources(), Stat.DEFENSE, pokemon.getStats()),
                standaloneAttribute(context.getResources(), Stat.SP_ATTACK, pokemon.getStats()),
                standaloneAttribute(context.getResources(), Stat.SP_DEFENSE, pokemon.getStats()),
                standaloneAttribute(context.getResources(), Stat.SPEED, pokemon.getStats())
        );
        return viewRepresentation;
    }

    private String totalStatsAttribute( Resources resources, StatsSet statsSet) {
        return resources.getString(R.string.pokemon_item_total_stats_tag) + statsSet.getTotalStats();
    }

    private String standaloneAttribute(Resources resources, Stat stat, StatsSet statsSet) {
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

    private TypesPresentation typeAttribute(
            Context context, Pair<Type, Type> type ){
        TypePresentation first = TypePresentation.TypePresentationBuilder.build(context, type.first);
        TypePresentation second = TypePresentation.TypePresentationBuilder.build(context, type.second);
        String weakAgainst = TypePresentation.TypePresentationBuilder.buildWeakTo(context, type);
        String resistantTo = TypePresentation.TypePresentationBuilder
                .buildResistantTo(context, type);
        return new TypesPresentation( first, second, weakAgainst, resistantTo );
    }
}
