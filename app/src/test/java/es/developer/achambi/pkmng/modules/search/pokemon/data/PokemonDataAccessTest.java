package es.developer.achambi.pkmng.modules.search.pokemon.data;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.db.dao.PokemonDAO;
import es.developer.achambi.pkmng.core.db.model.pokemon_species;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.model.Type;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PokemonDataAccessTest {
    private PokemonDataAccess pokemonDataAccess;
    private PokemonDAO mockDao;
    private IStatDataAccess mockStatDataAccess;
    private ITypeDataAccess mockTypeDataAccess;

    @Before
    public void setup() {
        mockDao = mock( PokemonDAO.class );
        mockStatDataAccess = mock( IStatDataAccess.class );
        mockTypeDataAccess = mock( ITypeDataAccess.class );
        pokemonDataAccess = new PokemonDataAccess( mockDao, mockStatDataAccess, mockTypeDataAccess );
    }

    @Test
    public void accessPokemonData() {
        when( mockDao.getPokemon( 1 ) ).thenReturn( buildRawPokemon() );
        when( mockStatDataAccess.accessPokemonStatsData( 1 ) ).thenReturn( new StatsSet() );
        when( mockTypeDataAccess.accessPokemonTypeData( 1 ) ).thenReturn(
                new Pair<>( Type.ELECTRIC, Type.WATER )  );

        Pokemon pokemon = pokemonDataAccess.accessPokemonData( 1 );

        assertPokemon( pokemon );
    }

    @Test
    public void accessPokemonDataIDNotFound() {
        when( mockDao.getPokemon( 1 ) ).thenReturn( null );

        Pokemon pokemon = pokemonDataAccess.accessPokemonData( 1 );

        assertEquals( new Pokemon(), pokemon );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonDataID0() {
        pokemonDataAccess.accessPokemonData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonDataIDNegative() {
        pokemonDataAccess.accessPokemonData( -1 );
    }

    @Test
    public void accessData() {
        ArrayList<pokemon_species> rawPokemonList = new ArrayList<>();
        rawPokemonList.add( buildRawPokemon() );
        when( mockDao.getPokemon() ).thenReturn( rawPokemonList );
        when( mockStatDataAccess.accessPokemonStatsData( 1 ) ).thenReturn( new StatsSet() );
        when( mockTypeDataAccess.accessPokemonTypeData( 1 ) ).thenReturn(
                new Pair<>( Type.ELECTRIC, Type.WATER )  );

        ArrayList<Pokemon> pokemonList = pokemonDataAccess.accessData();

        assertEquals( 1, pokemonList.size() );
        assertPokemon( pokemonList.get(0) );
    }

    @Test
    public void accessDataEmptyResult() {
        when( mockDao.getPokemon() ).thenReturn( new ArrayList<pokemon_species>() );

        ArrayList<Pokemon> pokemonList = pokemonDataAccess.accessData();

        assertTrue( pokemonList.isEmpty() );
    }

    private void assertPokemon( Pokemon pokemon ) {
        assertEquals( 1, pokemon.getId() );
        assertEquals( "pokemon", pokemon.getName() );
        assertEquals( new StatsSet(), pokemon.getStats() );
        assertEquals( new Pair<>(Type.ELECTRIC, Type.WATER), pokemon.getType() );
    }

    private pokemon_species buildRawPokemon() {
        pokemon_species pokemon = new pokemon_species();
        pokemon.id = 1;
        pokemon.identifier = "pokemon";

        return pokemon;
    }
}