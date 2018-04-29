package es.developer.achambi.pkmng.modules.data.type;

import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.modules.overview.model.Type;

public interface ITypeDataAccess {
    Type accessTypeData(int typeId );
    Pair<Type, Type> accessPokemonTypeData(int pokemonId );
}
