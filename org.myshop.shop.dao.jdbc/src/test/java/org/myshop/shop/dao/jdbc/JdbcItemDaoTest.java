package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private static final String TEST_PRODUCT_GROUP_ID = "test_product_group_id";
    private static final String TEST_PRODUCT_GROUP_DESCRIPTION = "test_product_group_description";
    private static final String TEST_ITEM_CATEGORY_ID = "test_item_category_id";
    private static final String TEST_ITEM_CATEGORY_NAME = "test_item_category_name";
    private static final String TEST_ITEM_CATEGORY_DESCRIPTION = "test_item_category_description";
    private static final float TEST_PURCHASE_PRICE = 123.123f;
    private static final float TEST_SALES_PRICE = 234.234f;
    
    @Mock
    private Connection sqlConnectionMock;
    
    @Mock
    private Item itemMock;
    
    @Mock
    private ProductGroup productGroup;
    
    @Mock
    private ItemCategory itemCategory;
    
    @Mock
    private ResultSet readItemResultSetMock;
    
    @Mock
    private ResultSet productGroupResultSetMock;
    
    @Mock
    private ResultSet itemCategoryResulSetMock;
    
    @Mock
    private PreparedStatement preparedStatementMock;
    
    @Mock
    private PreparedStatement readItemPreparedStatementMock;
    
    @Mock
    private PreparedStatement getItemPreparedStatementMock;
    
    @Mock
    private PreparedStatement getProductGroupPreparedStatementMock;
    
    @Mock
    private PreparedStatement getItemCategoryPreparedStatementMock;
    
    @Mock
    private List<Item> listMock;

    @Mock
    private JdbcItemDao itemDao;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(sqlConnectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcItemDao.READ_QUERY)).thenReturn(readItemPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcItemDao.GET_QUERY)).thenReturn(getItemPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcProductGroupDao.GET_QUERY)).thenReturn(getProductGroupPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcItemCategoryDao.GET_QUERY)).thenReturn(getItemCategoryPreparedStatementMock);
        
        
        when(readItemPreparedStatementMock.executeQuery()).thenReturn(readItemResultSetMock);
        when(getItemPreparedStatementMock.executeQuery()).thenReturn(readItemResultSetMock);
        when(getProductGroupPreparedStatementMock.executeQuery()).thenReturn(productGroupResultSetMock);
        when(getItemCategoryPreparedStatementMock.executeQuery()).thenReturn(itemCategoryResulSetMock);
        
        when(readItemResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        
        when(readItemResultSetMock.getString("id")).thenReturn(TEST_ITEM_ID);
        when(readItemResultSetMock.getString("name")).thenReturn(TEST_ITEM_NAME);
        when(readItemResultSetMock.getString("description")).thenReturn(TEST_ITEM_DESCRIPTION);
        when(readItemResultSetMock.getString("productGroup_id")).thenReturn(TEST_PRODUCT_GROUP_ID);
        when(readItemResultSetMock.getString("itemCategory_id")).thenReturn(TEST_ITEM_CATEGORY_ID);
        when(readItemResultSetMock.getFloat("purchasePrice")).thenReturn(TEST_PURCHASE_PRICE);
        when(readItemResultSetMock.getFloat("salesPrice")).thenReturn(TEST_SALES_PRICE);
        
        when(productGroupResultSetMock.getString("id")).thenReturn(TEST_PRODUCT_GROUP_ID);
        when(productGroupResultSetMock.getString("description")).thenReturn(TEST_PRODUCT_GROUP_DESCRIPTION);
        when(productGroupResultSetMock.getString("itemCategory_id")).thenReturn(TEST_ITEM_CATEGORY_ID);
        
        when(itemCategoryResulSetMock.getString("id")).thenReturn(TEST_ITEM_CATEGORY_ID);
        when(itemCategoryResulSetMock.getString("name")).thenReturn(TEST_ITEM_CATEGORY_NAME);
        when(itemCategoryResulSetMock.getString("description")).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
        
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
        
        verify(sqlConnectionMock).prepareStatement(JdbcItemDao.CREATE_QUERY);
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
    	List<Item> itemList = itemDao.read();
    	
    	verify(sqlConnectionMock).prepareStatement(JdbcItemDao.READ_QUERY);
    	
    	verify(readItemPreparedStatementMock).executeQuery();
    	
    	verify(readItemResultSetMock, times(2)).next();
    	
    	assertNotNull("Read should not return a null result, even when there are no items", itemList);
    	assertEquals(1, itemList.size());
    	
    	Item item = itemList.get(0);
    	
    	assertEquals(TEST_ITEM_ID, item.getId());
    	assertEquals(TEST_ITEM_NAME, item.getName());
    	assertEquals(TEST_ITEM_DESCRIPTION, item.getDescription());
    	assertEquals(TEST_ITEM_CATEGORY_ID, item.getItemCategory().getId());
    	assertEquals(TEST_ITEM_CATEGORY_NAME, item.getItemCategory().getName());
    	assertEquals(TEST_ITEM_CATEGORY_DESCRIPTION, item.getItemCategory().getDescription());
    	assertEquals(TEST_PRODUCT_GROUP_ID, item.getProductGroup().getId());
    	assertEquals(TEST_PRODUCT_GROUP_DESCRIPTION, item.getProductGroup().getDescription());
    	assertEquals(TEST_SALES_PRICE, item.getSalesPrice(), 0f);
    	assertEquals(TEST_PURCHASE_PRICE, item.getPurchasePrice(), 0f);
    }
    
    @Test
    public void testGet() throws SQLException{
    	itemDao.get(TEST_ITEM_ID);
    	
    	verify(sqlConnectionMock).prepareStatement(JdbcItemDao.GET_QUERY);
    	
    	//TODO
    	//complete following the style of read method
     }
    
    @Test
    public void testUpdate() throws SQLException{
    	itemDao.update(itemMock);
    	
    	verify(sqlConnectionMock).prepareStatement(JdbcItemDao.UPDATE_QUERY);
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
    	
    	verify(sqlConnectionMock).prepareStatement(JdbcItemDao.DELETE_QUERY);
    	verify(preparedStatementMock).setString(1, itemMock.getId());
    	verify(preparedStatementMock).executeUpdate();
    	
    }
    
    
}
