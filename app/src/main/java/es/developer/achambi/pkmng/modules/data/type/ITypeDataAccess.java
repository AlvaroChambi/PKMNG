package es.developer.achambi.pkmng.modules.data.type;

import android.support.v4.util.Pair;

import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public interface ITypeDataAccess {
    /**
     * Access the type data for the given id
     * @param typeId of the request Type data
     * @return Type data when id is found, Empty type otherwise
     * @throws IllegalIDException on invalid id, 0 or negative values
     */
    Type accessTypeData(int typeId ) throws IllegalIDException;

    /**
     * Access the type data for the given pokemon id
     * @param pokemonId of the request type
     * @return Pair of types related with the given pokemon id when the id is found,
     * empty Types otherwise
     * @throws IllegalIDException on invalid id, 0 or negative values
     */
    Pair<Type, Type> accessPokemonTypeData(int pokemonId )throws IllegalIDException;
}
