package org.myshop.shop.dao.jdbc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public class JdbcItemDaoTest {

    private static final String TEST_ITEM_ID = "test_item_id";
    private static final String TEST_ITEM_NAME = "test_item_name";
    private static final String TEST_ITEM_DESCRIPTION = "test_item_description";
    
    @Mock
    private Connection sqlConnectionMock;
    
    @Mock
    private Item itemMock;
    
    @Mock
    private ProductGroup productGroup;
    
    @Mock
    private ItemCategory itemCategory;
    
    @Mock
    private ResultSet rsMock;
    
    @Mock
    private PreparedStatement preparedStatementMock;
    
    @Mock
    private List<Item> listMock;

    @Mock
    private JdbcItemDao itemDao;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(sqlConnectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(rsMock);
        
        when(itemMock.getId()).thenReturn(TEST_ITEM_ID);
        when(itemMock.getName()).thenReturn(TEST_ITEM_NAME);
        when(itemMock.getDescription()).thenReturn(TEST_ITEM_DESCRIPTION);
        when(itemMock.getProductGroup()).thenReturn(productGroup);
        when(itemMock.getItemCategory()).thenReturn(itemCategory);
        when(itemMock.getPurchasePrice()).thenReturn(0.0f);
        when(itemMock.getSalesPrice()).thenReturn(0.0f);
        itemDao = new JdbcItemDao(sqlConnectionMock);        
    }
    
    @Test
    public void testCreate() throws SQLException {
        itemDao.create(itemMock);
        
        verify(sqlConnectionMock).prepareStatement(itemDao.CREATE_QUERY);
        verify(preparedStatementMock).setString(1, TEST_ITEM_ID);
        verify(preparedStatementMock).setString(2, TEST_ITEM_NAME);
        verify(preparedStatementMock).setString(3, TEST_ITEM_DESCRIPTION);
        verify(preparedStatementMock).setString(4, itemMock.getProductGroup().getId());
        verify(preparedStatementMock).setString(5, itemMock.getItemCategory().getId());
        verify(preparedStatementMock).setFloat(6, itemMock.getPurchasePrice());
        verify(preparedStatementMock).setFloat(7, itemMock.getSalesPrice());
        verify(preparedStatementMock).executeUpdate();
    }
    
    @Test
    public void testRead() throws SQLException{
    	itemDao.read();
    	
    	verify(sqlConnectionMock).prepareStatement(itemDao.READ_QUERY);
    	verify(preparedStatementMock).executeQuery();
    	verify(rsMock).next();
    	
    	assertNotNull(itemDao.read());
    }
    
    @Test
    public void testGet() throws SQLException{
    	itemDao.get(TEST_ITEM_ID);
    	
    	verify(sqlConnectionMock).prepareStatement(itemDao.GET_QUERY);
    	verify(preparedStatementMock).setString(1, TEST_ITEM_ID);
    	verify(preparedStatementMock).executeQuery();
     }
    
    @Test
    public void testUpdate() throws SQLException{
    	itemDao.update(itemMock);
    	
    	verify(sqlConnectionMock).prepareStatement(itemDao.UPDATE_QUERY);
    	verify(preparedStatementMock).setString(1, itemMock.getId());
    	verify(preparedStatementMock).setString(2, itemMock.getName());
    	verify(preparedStatementMock).setString(3, itemMock.getDescription());
    	verify(preparedStatementMock).setString(4, itemMock.getProductGroup().getId());
    	verify(preparedStatementMock).setString(5, itemMock.getItemCategory().getId());
    	verify(preparedStatementMock).setFloat(6, itemMock.getPurchasePrice());
    	verify(preparedStatementMock).setFloat(7, itemMock.getSalesPrice());
    	verify(preparedStatementMock).setString(8, itemMock.getId());
    	verify(preparedStatementMock).executeUpdate();
    }
    
    @Test
    public void testDelete() throws SQLException{
    	itemDao.delete(itemMock);
    	
    	verify(sqlConnectionMock).prepareStatement(itemDao.DELETE_QUERY);
    	verify(preparedStatementMock).setString(1, itemMock.getId());
    	verify(preparedStatementMock).executeUpdate();
    	
    }
    
    
}
