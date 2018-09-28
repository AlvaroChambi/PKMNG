package es.developer.achambi.pkmng.modules.search.nature.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.developer.achambi.coreframework.db.dao.NaturesDAO;
import es.developer.achambi.coreframework.db.model.natures;
import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NatureDataAccessTest {
    private NatureDataAccess natureDataAccess;
    private IStatDataAccess mockStatDataAccess;
    private NaturesDAO mockDao;

    @Before
    public void setup() {
        mockDao = mock( NaturesDAO.class );
        mockStatDataAccess = mock( IStatDataAccess.class );
        natureDataAccess = new NatureDataAccess( mockDao, mockStatDataAccess );
    }

    @Test
    public void accessNatureData() {
        when( mockStatDataAccess.accessStatData( 5 ) ).thenReturn( Stat.ATTACK );
        when( mockStatDataAccess.accessStatData( 10 ) ).thenReturn( Stat.DEFENSE );
        when( mockDao.getNature( 1 ) ).thenReturn( buildRawNature() );

        Nature nature = natureDataAccess.accessNatureData( 1 );

        assertNature( nature );
    }

    @Test
    public void accessNatureDataIDNotFound() {
        when( mockDao.getNature( 1 ) ).thenReturn( null );

        Nature nature = natureDataAccess.accessNatureData( 1 );

        assertEquals( new Nature(), nature );
    }

    @Test(expected = IllegalIDException.class)
    public void accessNatureDataID0() {
        natureDataAccess.accessNatureData( 0 );
    }

    @Test
    public void accessNatureDataNOID() {
        Nature nature = natureDataAccess.accessNatureData( -1 );
        assertEquals( new Nature(), nature );
    }

    @Test(expected = IllegalIDException.class)
    public void accessNatureDataNegative() {
        natureDataAccess.accessNatureData( -2 );
    }

    @Test
    public void accessData() {
        when( mockStatDataAccess.accessStatData( 5 ) ).thenReturn( Stat.ATTACK );
        when( mockStatDataAccess.accessStatData( 10 ) ).thenReturn( Stat.DEFENSE );
        ArrayList<natures> rawNatures = new ArrayList<>();
        rawNatures.add( buildRawNature() );
        when( mockDao.getNatures() ).thenReturn( rawNatures );

        ArrayList<Nature> natures = natureDataAccess.accessData();

        assertEquals( 1, natures.size() );
        assertNature( natures.get(0) );
    }

    @Test
    public void accessDataEmptyResult() {
        when( mockDao.getNatures() ).thenReturn( new ArrayList<natures>() );

        ArrayList<Nature> natures = natureDataAccess.accessData();

        assertTrue( natures.isEmpty() );
    }

    @Test
    public void accessQueryData() {
        when( mockStatDataAccess.accessStatData( 5 ) ).thenReturn( Stat.ATTACK );
        when( mockStatDataAccess.accessStatData( 10 ) ).thenReturn( Stat.DEFENSE );
        ArrayList<natures> rawNatures = new ArrayList<>();
        rawNatures.add( buildRawNature() );
        when( mockDao.queryNatures( "%query%" ) ).thenReturn( rawNatures );

        ArrayList<Nature> natures = natureDataAccess.accessNatureQueryData( "query" );

        verify( mockDao, times(1) ).queryNatures( "%query%" );
        assertEquals( 1, natures.size() );
        assertNature( natures.get(0) );
    }

    @Test
    public void accessQueryDataEmptyResult() {
        when( mockDao.queryNatures( "%query%" ) ).thenReturn( new ArrayList<natures>() );

        ArrayList<Nature> natures = natureDataAccess.accessNatureQueryData( "query" );

        verify( mockDao, times(1) ).queryNatures( "%query%" );
        assertTrue( natures.isEmpty() );
    }

    @Test
    public void accessQueryDataNullQuery() {
        ArrayList<Nature> natures = natureDataAccess.accessNatureQueryData( null );

        verify(mockDao, times(0)).queryNatures("%null%");
        assertTrue( natures.isEmpty() );
    }

    @Test
    public void accessNatureDataNeutralNature() {
        ArrayList<natures> rawNatures = new ArrayList<>();
        rawNatures.add( buildRawNeutralNature() );
        when( mockDao.getNatures() ).thenReturn( rawNatures );

        ArrayList<Nature> natures = natureDataAccess.accessData();

        assertEquals( 1, natures.size() );
        assertNeutralNature( natures.get(0) );
    }

    private void assertNature( Nature nature ) {
        assertEquals( 1, nature.getId() );
        assertEquals( "nature", nature.getName() );
        assertEquals( Stat.ATTACK, nature.getIncreasedStat() );
        assertEquals( Stat.DEFENSE, nature.getDecreasedStat() );
    }

    private void assertNeutralNature( Nature nature ) {
        assertEquals( 1, nature.getId() );
        assertEquals( "nature", nature.getName() );
        assertEquals( Stat.NONE, nature.getIncreasedStat() );
        assertEquals( Stat.NONE, nature.getDecreasedStat() );
    }

    private natures buildRawNature() {
        natures rawNature = new natures();
        rawNature.id = 1;
        rawNature.identifier = "nature";
        rawNature.increased_stat_id = 5;
        rawNature.decreased_stat_id = 10;

        return rawNature;
    }

    private natures buildRawNeutralNature() {
        natures rawNature = buildRawNature();
        rawNature.increased_stat_id = 5;
        rawNature.decreased_stat_id = 5;

        return rawNature;
    }
}