package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.res.Resources;
import android.util.Pair;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.details.view.representation.DetailsConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class ConfigurationDetailsDataBuilder {
    private Resources resources;

    public DetailsConfigurationRepresentation buildViewRepresentation(Resources resources,
                                                                      PokemonConfig configuration ) {
        this.resources = resources;

        DetailsConfigurationRepresentation view = new DetailsConfigurationRepresentation(
                configuration.getId(),
                configuration.getName(),
                configuration.getPokemon().getImageURL(),
                configuration.getPokemon().getName(),
                typeAttribute(configuration.getPokemon().getType()),
                formatAbility( configuration.getAbility(), resources ),
                formatItem( configuration.getItem(), resources ),
                formatNature( configuration.getNature(), resources ),
                configuration.getConfiguration().getMove0().getName(),
                configuration.getConfiguration().getMove1().getName(),
                configuration.getConfiguration().getMove2().getName(),
                configuration.getConfiguration().getMove3().getName(),
                standaloneAttribute(Stat.HP, configuration.getHP()),
                standaloneAttribute(Stat.ATTACK, configuration.getAttack()),
                standaloneAttribute(Stat.DEFENSE, configuration.getDefense()),
                standaloneAttribute(Stat.SP_ATTACK, configuration.getSpAttack()),
                standaloneAttribute(Stat.SP_DEFENSE, configuration.getDefense()),
                standaloneAttribute(Stat.SPEED, configuration.getSpeed())
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

    private String standaloneAttribute(Stat stat, int value) {
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
