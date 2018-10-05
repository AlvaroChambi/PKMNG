package es.developer.achambi.pkmng.modules.data.type;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.developer.achambi.pkmng.database.dao.TypeDAO;
import es.developer.achambi.pkmng.database.model.type_value;
import es.developer.achambi.pkmng.database.model.types;
import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Type;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TypeDataAccessTest {
    private TypeDataAccess typeDataAccess;
    private TypeDAO mockDAO;

    @Before
    public void setup() {
        mockDAO = mock(TypeDAO.class);
        typeDataAccess = new TypeDataAccess( mockDAO );
    }

    @Test
    public void accessTypeData() {
        types rawType = new types();
        rawType.identifier = "electric";
        rawType.id = 1;
        when( mockDAO.getType( 1 ) ).thenReturn( rawType );

        Type type = typeDataAccess.accessTypeData( 1 );

        assertEquals( Type.ELECTRIC, type );
    }

    @Test
    public void accessTypeDataIDNotFound() {
        when( mockDAO.getType( 1 ) ).thenReturn( null );

        Type type = typeDataAccess.accessTypeData( 1 );

        assertEquals( Type.EMPTY, type );
    }

    @Test(expected = IllegalIDException.class)
    public void accessTypeDataID0() {
        typeDataAccess.accessTypeData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessTypeDataIDNegative() {
        typeDataAccess.accessTypeData( -1 );
    }

    @Test
    public void accessPokemonTypeDataSingleType() {
        ArrayList<type_value> rawTypes = new ArrayList<>();
        type_value rawType = new type_value();
        rawType.type_id = 1;
        rawType.name = "electric";
        rawTypes.add( rawType );
        when( mockDAO.getPokemonType( 1 ) ).thenReturn( rawTypes );

        Pair<Type, Type> types = typeDataAccess.accessPokemonTypeData( 1 );

        assertEquals( Type.ELECTRIC, types.first );
        assertEquals( Type.EMPTY, types.second );
    }

    @Test
    public void accessPokemonTypeDataMultiType() {
        ArrayList<type_value> rawTypes = new ArrayList<>();
        type_value rawType = new type_value();
        rawType.type_id = 1;
        rawType.name = "electric";
        type_value rawType2 = new type_value();
        rawType2.type_id = 2;
        rawType2.name = "water";
        rawTypes.add( rawType );
        rawTypes.add( rawType2 );
        when( mockDAO.getPokemonType( 1 ) ).thenReturn( rawTypes );

        Pair<Type, Type> types = typeDataAccess.accessPokemonTypeData( 1 );

        assertEquals( Type.ELECTRIC, types.first );
        assertEquals( Type.WATER, types.second );
    }

    @Test
    public void accessPokemonTypeDataIDNotFound() {
        when( mockDAO.getPokemonType( 1 ) ).thenReturn( new ArrayList<type_value>() );

        Pair<Type, Type> types = typeDataAccess.accessPokemonTypeData( 1 );

        assertEquals( Type.EMPTY, types.first );
        assertEquals( Type.EMPTY, types.second );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonTypeDataID0() {
        typeDataAccess.accessPokemonTypeData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonTypeDataIDNegative() {
        typeDataAccess.accessPokemonTypeData( -1 );
    }
}