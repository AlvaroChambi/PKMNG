package es.developer.achambi.pkmng.modules.search.nature.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.db.dao.NaturesDAO;
import es.developer.achambi.pkmng.core.db.model.natures;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.stat.IStatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
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

    @Test(expected = IllegalIDException.class)
    public void accessNatureDataNegative() {
        natureDataAccess.accessNatureData( -1 );
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

    private void assertNature( Nature nature ) {
        assertEquals( 1, nature.getId() );
        assertEquals( "nature", nature.getName() );
        assertEquals( Stat.ATTACK, nature.getIncreasedStat() );
        assertEquals( Stat.DEFENSE, nature.getDecreasedStat() );
    }

    private natures buildRawNature() {
        natures rawNature = new natures();
        rawNature.id = 1;
        rawNature.identifier = "nature";
        rawNature.increased_stat_id = 5;
        rawNature.decreased_stat_id = 10;

        return rawNature;
    }
}