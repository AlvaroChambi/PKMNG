package es.developer.achambi.pkmng.modules.details.databuilder;

import android.content.res.Resources;
import android.util.Pair;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.details.view.representation.DetailsConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;

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
                resources.getString(R.string.pokemon_item_ability_tag)
                        + configuration.getAbility(),
                resources.getString(R.string.pokemon_item_item_tag)
                        + configuration.getItem(),
                resources.getString(R.string.pokemon_item_nature_tag)
                        + configuration.getNature(),
                configuration.getConfiguration().getMove0(),
                configuration.getConfiguration().getMove1(),
                configuration.getConfiguration().getMove2(),
                configuration.getConfiguration().getMove3(),
                standaloneAttribute(Stat.HP, configuration.getHP()),
                standaloneAttribute(Stat.ATTACK, configuration.getAttack()),
                standaloneAttribute(Stat.DEFENSE, configuration.getDefense()),
                standaloneAttribute(Stat.SP_ATTACK, configuration.getSpAttack()),
                standaloneAttribute(Stat.SP_DEFENSE, configuration.getDefense()),
                standaloneAttribute(Stat.SPEED, configuration.getSpeed())
        );

        return view;
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
}
