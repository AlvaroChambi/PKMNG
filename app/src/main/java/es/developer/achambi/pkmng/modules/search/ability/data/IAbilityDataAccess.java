package es.developer.achambi.pkmng.modules.search.ability.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public interface IAbilityDataAccess {
    ArrayList<Ability> accessAbilities(int pokemonId );
    Ability accessAbilityData( int abilityId );
}
