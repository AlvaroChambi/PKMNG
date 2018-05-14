package es.developer.achambi.pkmng.modules.data.type;

import android.support.v4.util.Pair;

import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.TypeDAO;
import es.developer.achambi.pkmng.core.db.model.type_value;
import es.developer.achambi.pkmng.core.db.model.types;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class TypeDataAccess implements ITypeDataAccess{
    private TypeDAO typeDAO;

    public TypeDataAccess(TypeDAO typeDAO) {
        this.typeDAO = typeDAO;
    }

    @Override
    public Type accessTypeData( int typeId ) {
        if( typeId < 1 ) {
            throw new IllegalIDException( typeId );
        }

        types raw = typeDAO.getType( typeId );
        if( raw != null ) {
            return parseType( typeDAO.getType( typeId ).identifier );
        } else {
            return Type.EMPTY;
        }
    }

    @Override
    public Pair<Type, Type> accessPokemonTypeData( int pokemonId ) {
        if( pokemonId < 1 ) {
            throw new IllegalIDException( pokemonId );
        }
        List<type_value> type = typeDAO.getPokemonType(pokemonId);
        Type secondType = Type.EMPTY;
        Type primaryType = Type.EMPTY;
        if( !type.isEmpty() ) {
            primaryType = parseType(type.get(0).name);
        }
        if( type.size() > 1 ) {
            secondType = parseType( type.get(1).name );
        }
        return new Pair<>(primaryType, secondType);
    }

    private Type parseType( String identifier ) {
        if( identifier.equalsIgnoreCase(Type.NORMAL.toString()) ) {
            return Type.NORMAL;
        } else if( identifier.equalsIgnoreCase(Type.FIRE.toString()) ) {
            return Type.FIRE;
        } else if( identifier.equalsIgnoreCase(Type.WATER.toString()) ) {
            return Type.WATER;
        } else if( identifier.equalsIgnoreCase(Type.ELECTRIC.toString()) ) {
            return Type.ELECTRIC;
        } else if( identifier.equalsIgnoreCase(Type.GRASS.toString()) ) {
            return Type.GRASS;
        } else if( identifier.equalsIgnoreCase(Type.ICE.toString()) ) {
            return Type.ICE;
        } else if( identifier.equalsIgnoreCase(Type.FIGHTING.toString()) ) {
            return Type.FIGHTING;
        } else if( identifier.equalsIgnoreCase(Type.POISON.toString()) ) {
            return Type.POISON;
        } else if( identifier.equalsIgnoreCase(Type.GROUND.toString()) ) {
            return Type.GROUND;
        } else if( identifier.equalsIgnoreCase(Type.FLYING.toString()) ) {
            return Type.FLYING;
        } else if( identifier.equalsIgnoreCase(Type.PSYCHIC.toString()) ) {
            return Type.PSYCHIC;
        } else if( identifier.equalsIgnoreCase(Type.BUG.toString()) ) {
            return Type.BUG;
        } else if( identifier.equalsIgnoreCase(Type.ROCK.toString()) ) {
            return Type.ROCK;
        } else if( identifier.equalsIgnoreCase(Type.GHOST.toString()) ) {
            return Type.GHOST;
        } else if( identifier.equalsIgnoreCase(Type.DRAGON.toString()) ) {
            return Type.DRAGON;
        } else if( identifier.equalsIgnoreCase(Type.DARK.toString()) ) {
            return Type.DARK;
        } else if( identifier.equalsIgnoreCase(Type.STEEL.toString()) ) {
            return Type.STEEL;
        } else if( identifier.equalsIgnoreCase(Type.FAIRY.toString()) ) {
            return Type.FAIRY;
        } else {
            return Type.EMPTY;
        }
    }
}
