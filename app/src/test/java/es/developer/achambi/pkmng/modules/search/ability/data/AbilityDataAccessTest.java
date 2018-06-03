package es.developer.achambi.pkmng.modules.search.ability.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.db.dao.AbilitiesDAO;
import es.developer.achambi.pkmng.core.db.model.ability_value;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbilityDataAccessTest {
    private AbilityDataAccess abilityDataAccess;
    private AbilitiesDAO mockDao;

    @Before
    public void setup() {
        mockDao = mock( AbilitiesDAO.class );
        abilityDataAccess = new AbilityDataAccess( mockDao, new DataFormatUtil());
    }

    @Test
    public void accessAbilityData() {
        when( mockDao.getAbility( 1 ) ).thenReturn( buildRawAbility() );
        Ability ability = abilityDataAccess.accessAbilityData( 1 );

        assertAbility( ability );
    }

    @Test
    public void accessAbilityDataIDNotFound() {
        when( mockDao.getAbility( 1 ) ).thenReturn( null );

        Ability ability = abilityDataAccess.accessAbilityData( 1 );

        assertEquals( new Ability(), ability );
    }

    @Test(expected = IllegalIDException.class)
    public void accessAbilityDataID0() {
        abilityDataAccess.accessAbilityData( 0 );
    }

    @Test
    public void accessAbilityDataNOID() {
        Ability ability = abilityDataAccess.accessAbilityData( -1 );
        assertEquals( new Ability(), ability );
    }

    @Test(expected = IllegalIDException.class)
    public void accessAbilityDataIDNegative() {
        abilityDataAccess.accessAbilityData( -2 );
    }

    @Test
    public void accessAbilities() {
        ArrayList<ability_value> rawAbilities = new ArrayList<>();
        rawAbilities.add( buildRawAbility() );
        when( mockDao.getPokemonAbilities( 1 ) ).thenReturn( rawAbilities );

        ArrayList<Ability> abilities = abilityDataAccess.accessAbilities( 1 );

        assertEquals( 1, abilities.size() );
        assertAbility( abilities.get( 0 ) );
    }

    @Test
    public void accessAbilitiesIDNotFound() {
        when( mockDao.getPokemonAbilities( 1 ) ).thenReturn(
                new ArrayList<ability_value>() );

        ArrayList<Ability> abilities = abilityDataAccess.accessAbilities( 1 );

        assertEquals( true, abilities.isEmpty() );
    }

    @Test(expected = IllegalIDException.class)
    public void accessAbilitiesID0() {
        abilityDataAccess.accessAbilities( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessAbilitiesIDNegative() {
        abilityDataAccess.accessAbilities( -1 );
    }

    @Test
    public void accessQueryData() {
        ArrayList<ability_value> rawAbilities = new ArrayList<>();
        rawAbilities.add( buildRawAbility() );
        when( mockDao.getAbilitiesQuery( 1, "query%" ) ).thenReturn( rawAbilities );

        ArrayList<Ability> abilities = abilityDataAccess.accessAbilitiesQuery( 1,
                "query" );

        verify(mockDao, times(1)).getAbilitiesQuery(1,
                "query%");
        assertEquals(1, abilities.size());
        assertAbility( abilities.get(0) );
    }

    @Test
    public void accessQueryDataEmptyResult() {
        when( mockDao.getAbilitiesQuery(1, "query%") )
                .thenReturn(new ArrayList<ability_value>());

        ArrayList<Ability> abilities = abilityDataAccess.accessAbilitiesQuery(1,
                "query");

        verify(mockDao, times(1)).getAbilitiesQuery(1,
                "query%");
        assertTrue( abilities.isEmpty() );
    }

    @Test
    public void accessQueryDataNullQuery() {
        ArrayList<Ability> abilities = abilityDataAccess.accessAbilitiesQuery(1,
                null);

        verify(mockDao, times(0)).getAbilitiesQuery(1,
                "null%");
        assertTrue( abilities.isEmpty() );
    }

    @Test(expected = IllegalIDException.class)
    public void accessQueryDataInvalidID() {
        abilityDataAccess.accessAbilitiesQuery(-1,
                "query");
    }

    private void assertAbility( Ability ability ) {
        assertEquals( 1, ability.getId() );
        assertEquals( "effect", ability.getDescription() );
        assertEquals( "short effect", ability.getDescriptionShort() );
        assertEquals( "ability", ability.getName() );
    }

    private ability_value buildRawAbility() {
        ability_value ability = new ability_value();
        ability.id = 1;
        ability.effect = "effect";
        ability.name = "ability";
        ability.shortEffect = "short effect";
        return ability;
    }
}