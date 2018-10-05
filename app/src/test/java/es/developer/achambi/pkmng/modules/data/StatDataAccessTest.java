package es.developer.achambi.pkmng.modules.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.database.dao.StatsDAO;
import es.developer.achambi.pkmng.database.model.configuration_stats;
import es.developer.achambi.pkmng.database.model.stat_value;
import es.developer.achambi.pkmng.database.model.stats;
import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.stat.StatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatDataAccessTest {
    private StatDataAccess statDataAccess;
    private StatsDAO mockDAO;

    @Before
    public void setup() {
        mockDAO = mock(StatsDAO.class);
        statDataAccess = new StatDataAccess( mockDAO );
    }

    @Test
    public void accessStatData() {
        when(mockDAO.getStat(1)).thenReturn( buildStat() );

        Stat result = statDataAccess.accessStatData( 1 );

        assertEquals( Stat.ATTACK, result );
    }

    @Test(expected = IllegalIDException.class)
    public void accessStatDataInvalidID0() {
        statDataAccess.accessStatData(0);
    }

    @Test(expected = IllegalIDException.class)
    public void setStatDataAccessInvalidIDNegative() {
        statDataAccess.accessStatData(-1);
    }

    @Test
    public void accessStatDataIDNotFound() {
        when(mockDAO.getStat(1)).thenReturn(null);
        Stat result = statDataAccess.accessStatData(1);
        assertEquals(Stat.NONE, result);
    }

    @Test
    public void accessPokemonStatsData() {
        ArrayList<stat_value> values = new ArrayList<>();
        values.add( buildAttackPokemonStat() );
        when(mockDAO.getStats(1)).thenReturn( values );

        StatsSet statsSet = statDataAccess.accessPokemonStatsData( 1 );
        assertStatSetAttack( statsSet );
    }

    @Test
    public void accessPokemonStatsDataIdNotFound() {
        when(mockDAO.getStats(1)).thenReturn(new ArrayList<stat_value>());

        StatsSet statsSet = statDataAccess.accessPokemonStatsData( 1 );
        assertEmptyStatSet( statsSet );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonStatsDataID0() {
        statDataAccess.accessPokemonStatsData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessPokemonStatsDataIDNegative() {
        statDataAccess.accessPokemonStatsData( -1 );
    }

    @Test
    public void accessEVSetData() {
        ArrayList<stat_value> values = new ArrayList<>();
        values.add( buildAttackPokemonStat() );
        when(mockDAO.getEvsSet( 1 )).thenReturn( values );

        StatsSet statsSet = statDataAccess.accessEvsSetData( 1 );
        assertStatSetAttack( statsSet );
    }

    @Test
    public void accessEvSetDataIDNotFound() {
        when(mockDAO.getEvsSet( 1 )).thenReturn( new ArrayList<stat_value>() );
        StatsSet statsSet = statDataAccess.accessEvsSetData( 1 );
        assertEmptyStatSet( statsSet );
    }

    @Test(expected = IllegalIDException.class)
    public void accessEvStatsDataID0() {
        statDataAccess.accessStatData( 0 );
    }

    @Test(expected = IllegalIDException.class)
    public void accessEvStatsDataIDNegative() {
        statDataAccess.accessEvsSetData( -1 );
    }

    @Test
    public void insertStatSet() {
        StatsSet statsSet = new StatsSet();
        statsSet.setAttack( 100 );
        ArgumentCaptor<List<configuration_stats>> captor = ArgumentCaptor.forClass(List.class);
        doNothing().when(mockDAO).insertStatsSet(captor.capture());

        statDataAccess.insertStatsSet( 1, statsSet );

        assertConfigurationStat( captor.getValue() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertStatSetIDNegative() {
        statDataAccess.insertStatsSet( -1, new StatsSet() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertStatSetNullStatSet() {
        statDataAccess.insertStatsSet( 1, null );
    }

    @Test
    public void updateStatSet() {
        StatsSet statsSet = new StatsSet();
        statsSet.setAttack( 100 );
        ArgumentCaptor<List<configuration_stats>> captor = ArgumentCaptor.forClass(List.class);
        doNothing().when(mockDAO).updateStatsSet(captor.capture());

        statDataAccess.updateStatsSet( 1, statsSet );

        assertConfigurationStat( captor.getValue() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatSetIDNegative() {
        statDataAccess.updateStatsSet( -1, new StatsSet() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatSetNullStatSet() {
        statDataAccess.updateStatsSet( 1, null );
    }

    private void assertConfigurationStat( List<configuration_stats> values ) {
        for( configuration_stats stat : values ) {
            if( stat.stat_id == 2 ) {
                assertEquals( 1, stat.configuration_id );
                assertEquals( 100, stat.ev_value );
            }
        }
    }

    private void assertEmptyStatSet( StatsSet statsSet ) {
        assertEquals( 0, statsSet.getHP() );
        assertEquals( 0, statsSet.getAttack() );
        assertEquals( 0, statsSet.getDefense() );
        assertEquals( 0, statsSet.getSpAttack() );
        assertEquals( 0, statsSet.getSPDefense() );
        assertEquals( 0, statsSet.getSpeed() );
    }

    private void assertStatSetAttack( StatsSet statsSet ) {
        assertEquals( 0, statsSet.getHP() );
        assertEquals( 100, statsSet.getAttack() );
        assertEquals( 0, statsSet.getDefense() );
        assertEquals( 0, statsSet.getSpAttack() );
        assertEquals( 0, statsSet.getSPDefense() );
        assertEquals( 0, statsSet.getSpeed() );
    }

    private stat_value buildAttackPokemonStat() {
        stat_value stat = new stat_value();
        stat.name = "attack";
        stat.stat_id = 1;
        stat.value = 100;
        return stat;
    }

    private stats buildStat() {
        stats stat = new stats();
        stat.id = 1;
        stat.identifier = "attack";
        return stat;
    }
}