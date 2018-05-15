package es.developer.achambi.pkmng.modules.search.ability.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public interface IAbilityDataAccess {
    /**
     * Access Abilities data for the given pokemon id
     * @param pokemonId to be searched, valid id's are higher than 0
     * @return Abilities data if the id is found, empty abilities otherwise
     * @throws IllegalIDException on 0 or negative id's values
     */
    ArrayList<Ability> accessAbilities(int pokemonId ) throws IllegalIDException;

    /**
     * Access Ability data with the given id
     * @param abilityId to be searched, valid id's are higher than 0
     * @return Ability data if the id is found, empty ability otherwise
     * @throws IllegalIDException on 0 or negative id's values
     */
    Ability accessAbilityData( int abilityId ) throws IllegalIDException;
}
