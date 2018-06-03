package es.developer.achambi.pkmng.modules.search.item.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.db.dao.ItemDAO;
import es.developer.achambi.pkmng.core.db.model.item_value;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemDataAccessTest {
    private ItemDataAccess itemDataAccess;
    private ItemDAO mockDao;

    @Before
    public void setup() {
        mockDao = mock( ItemDAO.class );
        itemDataAccess = new ItemDataAccess( mockDao, new DataFormatUtil());
    }

    @Test
    public void accessItemData() {
        when( mockDao.getItem( 1 ) ).thenReturn( buildRawItem() );

        Item item = itemDataAccess.accessItemData( 1 );

        assertItem( item );
    }

    @Test
    public void accessItemDataIDNotFound() {
        when( mockDao.getItem( 1 ) ).thenReturn( null );

        Item item = itemDataAccess.accessItemData( 1 );

        assertEquals( new Item(), item );
    }

    @Test(expected = IllegalIDException.class)
    public void accessItemDataID0() {
        itemDataAccess.accessItemData( 0 );
    }

    @Test
    public void accessItemDataNOID() {
        Item item = itemDataAccess.accessItemData( -1 );
        assertEquals( new Item(), item );
    }

    @Test(expected = IllegalIDException.class)
    public void accessItemDataIDNegative() {
        itemDataAccess.accessItemData( -2 );
    }

    @Test
    public void accessData() {
        ArrayList<item_value> rawItems = new ArrayList<>();
        rawItems.add( buildRawItem() );
        when( mockDao.getItems() ).thenReturn( rawItems );

        ArrayList<Item> items = itemDataAccess.accessData();

        assertEquals( 1, items.size() );
        assertItem( items.get(0) );
    }

    @Test
    public void accessDataEmptyResult() {
        when( mockDao.getItems() ).thenReturn( new ArrayList<item_value>() );

        ArrayList<Item> items = itemDataAccess.accessData();

        assertTrue( items.isEmpty() );
    }

    @Test
    public void accessQueryData() {
        ArrayList<item_value> rawItemsList = new ArrayList<>();
        rawItemsList.add( buildRawItem() );
        when( mockDao.queryItems( "query%" ) ).thenReturn( rawItemsList);

        ArrayList<Item> items = itemDataAccess.queryItemData( "query" );

        verify(mockDao, times(1)).queryItems("query%");
        assertEquals( 1, items.size() );
        assertItem( items.get(0) );
    }

    @Test
    public void accessQueryDataEmptyResult() {
        when( mockDao.queryItems( "query%" ) ).thenReturn( new ArrayList<item_value>());

        ArrayList<Item> items = itemDataAccess.queryItemData( "query" );

        verify(mockDao, times(1)).queryItems("query%");
        assertTrue( items.isEmpty() );
    }

    @Test
    public void accessQueryDataNullQuery() {
        ArrayList<Item> items = itemDataAccess.queryItemData(null);

        verify(mockDao, times(0)).queryItems("null%");
        assertTrue( items.isEmpty() );
    }

    private void assertItem( Item item ) {
        assertEquals( 1, item.getId() );
        assertEquals( "item", item.getName() );
        assertEquals( "effect", item.getDescription() );
        assertEquals( "short_effect", item.getDescriptionShort() );
    }

    private item_value buildRawItem() {
        item_value item = new item_value();
        item.id = 1;
        item.name = "item";
        item.effect = "effect";
        item.shortEffect = "short_effect";
        return item;
    }
}