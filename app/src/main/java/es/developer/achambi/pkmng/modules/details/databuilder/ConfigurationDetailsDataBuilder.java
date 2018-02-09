package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.modules.details.view.representation.DetailsConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class ConfigurationDetailsDataBuilder {

    public DetailsConfigurationRepresentation buildViewRepresentation(Context context,
                                                                      PokemonConfig configuration ) {

        DetailsConfigurationRepresentation view = new DetailsConfigurationRepresentation(
                configuration.getId(),
                configuration.getName(),
                configuration.getPokemon().getImageURL(),
                configuration.getPokemon().getName(),
                typeAttribute(context, configuration.getPokemon().getType()),
                formatAbility( configuration.getAbility(), context.getResources() ),
                formatItem( configuration.getItem(), context.getResources() ),
                formatNature( configuration.getNature(), context.getResources() ),
                configuration.getConfiguration().getMove0().getName(),
                configuration.getConfiguration().getMove1().getName(),
                configuration.getConfiguration().getMove2().getName(),
                configuration.getConfiguration().getMove3().getName(),
                standaloneAttribute(Stat.HP, configuration.getHP(), context.getResources()),
                standaloneAttribute(Stat.ATTACK, configuration.getAttack(), context.getResources()),
                standaloneAttribute(Stat.DEFENSE, configuration.getDefense(), context.getResources()),
                standaloneAttribute(Stat.SP_ATTACK, configuration.getSpAttack(), context.getResources()),
                standaloneAttribute(Stat.SP_DEFENSE, configuration.getDefense(), context.getResources()),
                standaloneAttribute(Stat.SPEED, configuration.getSpeed(), context.getResources())
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

    private String standaloneAttribute(Stat stat, int value, Resources resources) {
        switch (stat) {
            case HP:
                return resources.getString(R.string.pokemon_item_hp_tag) + value;
            case DEFENSE:
                return resources.getString(R.string.pokemon_item_attack_tag) + value;
            case ATTACK:
                return resources.getString(R.string.pokemon_item_defense_tag) + value;
            case SP_ATTACK:
                return resources.getString(R.string.pokemon_item_sp_attack_tag) + value;
            case SP_DEFENSE:
                return resources.getString(R.string.pokemon_item_sp_defense_tag) + value;
            case SPEED:
                return resources.getString(R.string.pokemon_item_speed_tag) + value;
            default:
                return "";
        }
    }

    private Pair<TypePresentation, TypePresentation> typeAttribute(
            Context context, Pair<Type, Type> type ){
        TypePresentation first = TypePresentation.TypePresentationBuilder.build(context, type.first);
        TypePresentation second = TypePresentation.TypePresentationBuilder.build(context, type.second);
        return new Pair<>( first, second );
    }
}
