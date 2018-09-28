package es.developer.achambi.pkmng.modules.search.ability.data;

import java.util.ArrayList;

import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public interface IAbilityDataAccess {
    /**
     * Access Abilities data for the given pokemon id
     * @param pokemonId to be searched, valid id's are higher than 0
     * @return Abilities data if the id is found, empty abilities otherwise
     * @throws IllegalIDException on 0 or negative id's values
     */
    ArrayList<Ability> accessAbilities( int pokemonId ) throws IllegalIDException;

    /**
     * Access the natures data for the given pokemon and query
     * @param pokemonId to be searched, valid id's are higher than 0
     * @param query text to perform the query, on a null query an empty result will be returned
     * @return available data that starts with the given query
     * @throws IllegalIDException on 0 or negative id's values
     */
    ArrayList<Ability> accessAbilitiesQuery( int pokemonId, String query ) throws IllegalIDException;

    /**
     * Access Ability data with the given id
     * @param abilityId to be searched, valid id's are higher than 0
     * @return Ability data if the id is found, empty ability otherwise,
     * empty Ability will also be returned on id equals to -1
     * @throws IllegalIDException on 0 or negative id's values
     */
    Ability accessAbilityData( int abilityId ) throws IllegalIDException;
}
