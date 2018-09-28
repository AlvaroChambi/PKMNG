package es.developer.achambi.pkmng.modules.search.configuration.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

import es.developer.achambi.coreframework.db.dao.ConfigurationDAO;
import es.developer.achambi.coreframework.db.model.configurations;
import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccess;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.data.IItemDataAccess;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.data.IMoveDataAccess;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.data.INatureDataAccess;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.search.pokemon.data.IPokemonDataAccess;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfigurationDataAccessTest {
    private ConfigurationDAO mockDao;
    private IPokemonDataAccess mockPokemonDataAccess;
    private IMoveDataAccess mockMoveDataAccess;

    private ConfigurationDataAccess configurationDataAccess;

    @Before
    public void setup() {
        mockDao = mock( ConfigurationDAO.class );
        mockPokemonDataAccess = mock( IPokemonDataAccess.class );
        mockMoveDataAccess = mock( IMoveDataAccess.class );

        configurationDataAccess = new ConfigurationDataAccess(
                mockDao,
                mockPokemonDataAccess,
                mockMoveDataAccess,
                mock( IItemDataAccess.class ),
                mock( IAbilityDataAccess.class ),
                mock( INatureDataAccess.class ),
                mock( IStatDataAccess.class ));
    }

    @Test
    public void accessConfigurationData() {
        ArrayList<configurations> rawConfigurations = new ArrayList<>();
        rawConfigurations.add( buildRawConfiguration() );
        when( mockDao.getConfigurations() ).thenReturn( rawConfigurations );
        when( mockPokemonDataAccess.accessPokemonData( 1 ) ).thenReturn( new Pokemon(1) );
        when( mockMoveDataAccess.accessMoveData( 1 ) ).thenReturn( new Move( 1 ));
        when( mockMoveDataAccess.accessMoveData( 2 ) ).thenReturn( new Move( 2 ));
        when( mockMoveDataAccess.accessMoveData( 3 ) ).thenReturn( new Move( 3 ));
        when( mockMoveDataAccess.accessMoveData( 4 ) ).thenReturn( new Move( 4 ));

        ArrayList<PokemonConfig> configuration = configurationDataAccess.accessConfigurationData();

        assertEquals( 1, configuration.size() );
        assertConfiguration( configuration.get(0) );
    }

    @Test
    public void queryConfigurationData() {
        ArrayList<configurations> rawConfigurations = new ArrayList<>();
        rawConfigurations.add( buildRawConfiguration() );
        when( mockDao.queryConfigurations( "%query%" ) ).thenReturn( rawConfigurations );
        when( mockPokemonDataAccess.accessPokemonData( 1 ) ).thenReturn( new Pokemon(1) );
        when( mockMoveDataAccess.accessMoveData( 1 ) ).thenReturn( new Move( 1 ));
        when( mockMoveDataAccess.accessMoveData( 2 ) ).thenReturn( new Move( 2 ));
        when( mockMoveDataAccess.accessMoveData( 3 ) ).thenReturn( new Move( 3 ));
        when( mockMoveDataAccess.accessMoveData( 4 ) ).thenReturn( new Move( 4 ));

        ArrayList<PokemonConfig> configuration =
                configurationDataAccess.queryConfigurationData( "query" );

        verify(mockDao, times(1)).queryConfigurations("%query%");
        assertEquals( 1, configuration.size() );
        assertConfiguration( configuration.get(0) );
    }

    @Test
    public void queryConfigurationDataEmptyResult() {
        when( mockDao.queryConfigurations("%query%") ).thenReturn( new ArrayList<configurations>() );

        ArrayList<PokemonConfig> configs = configurationDataAccess.queryConfigurationData( "query" );

        verify(mockDao, times(1)).queryConfigurations("%query%");
        assertTrue( configs.isEmpty() );
    }

    @Test
    public void queryConfigurationDataNullQuery() {
        ArrayList<PokemonConfig> pokemonList = configurationDataAccess.queryConfigurationData(null);

        verify(mockDao, times(0)).queryConfigurations("%null%");
        assertTrue( pokemonList.isEmpty() );
    }

    @Test
    public void accessConfigurationDataEmpty() {
        when( mockDao.getConfigurations() ).thenReturn( new ArrayList<configurations>() );

        ArrayList<PokemonConfig> configs = configurationDataAccess.accessConfigurationData();

        assertTrue( configs.isEmpty() );
    }

    @Test
    public void updateConfiguration() {
        ArgumentCaptor<configurations> captor = ArgumentCaptor.forClass(configurations.class);
        doNothing().when(mockDao).update( captor.capture() );

        configurationDataAccess.updateConfiguration( buildConfiguration() );

        assertRawConfiguration( captor.getValue() );
        assertEquals( 1, (int)captor.getValue().id );
    }

    @Test(expected = IllegalIDException.class)
    public void updateConfigurationIDNegative() {
        PokemonConfig pokemonConfig = new PokemonConfig();
        pokemonConfig.setId( -1 );
        configurationDataAccess.updateConfiguration( pokemonConfig );
    }

    @Test
    public void insertConfigurationSuccessful() {
        ArgumentCaptor<configurations> captor = ArgumentCaptor.forClass(configurations.class);
        when(mockDao.insert(captor.capture())).thenReturn(1l);
        PokemonConfig newConfig = buildConfiguration();
        newConfig.setId( -1 );
        configurationDataAccess.insertConfiguration( newConfig );

        assertRawConfiguration( captor.getValue() );
        assertEquals( null, captor.getValue().id );
    }

    @Test(expected = IllegalIDException.class)
    public void insertConfigurationIDInvalid() {
        PokemonConfig pokemonConfig = new PokemonConfig();
        pokemonConfig.setId( 0 );
        configurationDataAccess.insertConfiguration( pokemonConfig );
    }

    private PokemonConfig buildConfiguration() {
        PokemonConfig config = new PokemonConfig();
        config.setId( 1 );
        config.setName( "configuration" );
        config.setPokemon( new Pokemon(1) );
        Configuration configuration = new Configuration();
        config.setConfiguration( configuration );
        configuration.setMove0( new Move(1) );
        configuration.setMove1( new Move( 2 ) );
        configuration.setMove2( new Move( 3 ) );
        configuration.setMove3( new Move( 4 ) );
        Ability ability = new Ability();
        ability.setId( 1 );
        configuration.setAbility( ability );
        Nature nature = new Nature();
        nature.setId( 1 );
        configuration.setNature( nature );
        Item item = new Item();
        item.setId( 1 );
        configuration.setItem( item );

        return config;
    }

    private void assertRawConfiguration( configurations configuration ) {
        assertEquals( "configuration", configuration.name );
        assertEquals( 1, configuration.pokemon_id );
        assertEquals( 1, configuration.move_0_id );
        assertEquals( 2 , configuration.move_1_id );
        assertEquals( 3 , configuration.move_2_id );
        assertEquals( 4, configuration.move_3_id );
        assertEquals( 1, configuration.ability_id );
        assertEquals( 1, configuration.item_id );
        assertEquals( 1, configuration.nature_id );
    }

    private void assertConfiguration( PokemonConfig pokemonConfig ) {
        assertEquals( 1, pokemonConfig.getId() );
        assertEquals( "configuration", pokemonConfig.getName() );
        assertEquals( 1, pokemonConfig.getPokemon().getId() );
        assertEquals( 1, pokemonConfig.getConfiguration().getMove0().getId() );
        assertEquals( 2, pokemonConfig.getConfiguration().getMove1().getId() );
        assertEquals( 3, pokemonConfig.getConfiguration().getMove2().getId() );
        assertEquals( 4, pokemonConfig.getConfiguration().getMove3().getId() );
    }

    private configurations buildRawConfiguration() {
        configurations configuration = new configurations();
        configuration.id = 1;
        configuration.name = "configuration";
        configuration.pokemon_id = 1;
        configuration.move_0_id = 1;
        configuration.move_1_id = 2;
        configuration.move_2_id = 3;
        configuration.move_3_id = 4;
        configuration.nature_id = 1;
        configuration.item_id = 1;
        configuration.ability_id = 1;

        return configuration;
    }
}