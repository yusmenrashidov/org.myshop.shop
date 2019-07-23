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
import org.myshop.shop.model.ItemCategory;

public class JdbcItemCategoryDaoTest {

    private static final String TEST_ITEM_CATEGORY_ID = "test_itemCategory_id";
    private static final String TEST_ITEM_CATEGORY_NAME = "test_itemCategory_name";
    private static final String TEST_ITEM_CATEGORY_DESCRIPTION = "test_itemCategory_description";
    
    
    @Mock
    private Connection sqlConnectionMock;
    
    
    @Mock
    private ItemCategory itemCategoryMock;
    
    @Mock
    private ResultSet rsMock;
    
    @Mock
    private PreparedStatement preparedStatementMock;
    
    @Mock
    private List<ItemCategory> listMock;

    @Mock
    private JdbcItemCategoryDao itemCategoryDaoMock;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(sqlConnectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(rsMock);
        when(itemCategoryMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
        when(itemCategoryMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
        when(itemCategoryMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
       
        itemCategoryDaoMock = new JdbcItemCategoryDao(sqlConnectionMock);
    }
    
    @Test
    public void testCreate() throws SQLException {
        itemCategoryDaoMock.create(itemCategoryMock);
        
        verify(sqlConnectionMock).prepareStatement(anyString());
        verify(preparedStatementMock).setString(1, itemCategoryMock.getId());
        verify(preparedStatementMock).setString(2, itemCategoryMock.getName());
        verify(preparedStatementMock).setString(3, itemCategoryMock.getDescription());
        verify(preparedStatementMock).executeQuery();
    }
    
    @Test
    public void testRead() throws SQLException{
    	itemCategoryDaoMock.read();
    	
    	verify(sqlConnectionMock).prepareStatement(anyString());
    	verify(preparedStatementMock).executeQuery();
    	verify(rsMock).next();
    	
    	assertNotNull(itemCategoryDaoMock.read());
    }
    
    @Test
    public void testGet() throws SQLException{
    	itemCategoryDaoMock.get(itemCategoryMock.getId());
    	
    	verify(sqlConnectionMock).prepareStatement(anyString());
    	verify(preparedStatementMock).setString(1, itemCategoryMock.getId());
    	verify(preparedStatementMock).executeQuery();
     }
    
    @Test
    public void testUpdate() throws SQLException{
    	itemCategoryDaoMock.update(itemCategoryMock);
    	
    	verify(sqlConnectionMock).prepareStatement(anyString());
    	verify(preparedStatementMock).setString(1, itemCategoryMock.getId());
    	verify(preparedStatementMock).setString(2, itemCategoryMock.getName());
    	verify(preparedStatementMock).setString(3, itemCategoryMock.getDescription());
    	verify(preparedStatementMock).setString(4, itemCategoryMock.getId());
    	verify(preparedStatementMock).executeUpdate();
    }
    
    @Test
    public void testDelete() throws SQLException{
    	itemCategoryDaoMock.delete(itemCategoryMock);
    	
    	verify(sqlConnectionMock).prepareStatement(anyString());
    	verify(preparedStatementMock).setString(1, itemCategoryMock.getId());
    	verify(preparedStatementMock).executeQuery();
    	
    } 
}