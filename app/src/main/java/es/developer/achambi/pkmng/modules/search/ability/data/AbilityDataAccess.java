package es.developer.achambi.pkmng.modules.search.ability.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.ability_value;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public class AbilityDataAccess {
    private AppDatabase database;

    public AbilityDataAccess(AppDatabase database) {
        this.database = database;
    }

    public ArrayList<Ability> accessAbilities( int pokemonId ) {
        List<ability_value> rawAbilities = database.abilitiesModel().getPokemonAbilities(pokemonId);
        ArrayList<Ability> abilities = new ArrayList<>(rawAbilities.size());
        for (ability_value currentAbility : rawAbilities) {
            Ability ability = new Ability();
            ability.setId( currentAbility.id );
            ability.setName( currentAbility.name );
            ability.setDescription( currentAbility.effect );
            ability.setDescriptionShort( currentAbility.shortEffect );

            abilities.add( ability );
        }
        return abilities;
    }

    public Ability accessAbilityData( int abilityId ) {
        ability_value rawAbility = database.abilitiesModel().getAbility(abilityId);
        Ability ability = new Ability();
        if(rawAbility != null) {
            ability.setId( rawAbility.id );
            ability.setName( rawAbility.name );
            ability.setDescription( rawAbility.effect );
            ability.setDescriptionShort( rawAbility.shortEffect );
        }
        return ability;
    }
}
