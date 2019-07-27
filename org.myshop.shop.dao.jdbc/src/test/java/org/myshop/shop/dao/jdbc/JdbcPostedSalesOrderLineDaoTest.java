package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;
import org.myshop.shop.model.PostedSalesOrderLine;

public class JdbcPostedSalesOrderLineDaoTest {

	@Mock
	private Connection sqlConnectionMock;
	
	@Mock
	private PreparedStatement createPreparedStatementMock;
	
	@Mock
	private PreparedStatement readPreparedStatementMock;
	
	@Mock
	private PreparedStatement getPreparedStatementMock;
	
	@Mock
	private PreparedStatement updatePreparedStatementMock;
	
	@Mock
	private PreparedStatement deletePreparedStatementMock;
	
	@Mock
	private PreparedStatement getItemPreparedStatementMock;
	
	@Mock
	private PreparedStatement getProductGroupPreparedStatementMock;
	    
	@Mock
	private PreparedStatement getItemCategoryPreparedStatementMock;
	
	@Mock
	private ResultSet resultSetMock;
	
	@Mock
	private ResultSet itemResultSetMock;
	
    	@Mock
   	private ResultSet productGroupResultSetMock;
	    
   	@Mock
	private ResultSet itemCategoryResulSetMock;
	
	@Mock
	private JdbcPostedSalesOrderLineDao postedSalesOrderLineDaoMock;
	
	@Mock
	private JdbcItemDao itemDaoMock;
	
	@Mock
	private PostedSalesOrderLine postedSalesOrderLineMock;
	
	@Mock
	private Item itemMock;
	
	@Mock
	private ProductGroup productGroupMock;
	
	@Mock
	private ItemCategory itemCategoryMock;
	
	private static final String TEST_POSTED_SALES_ORDER_LINE_ID = "test_salesOrderLine_id";
	private static final int TEST_POSTED_SALES_ORDER_LINE_LINE_NUMBER = (int) 123d;
	private static final int TEST_POSTED_SALES_ORDER_LINE_QUANTITY = (int) 123d;
	private static final float TEST__POSTED_SALES_ORDER_LINE_PRICE = 123.456f;
	private static final int TEST_POSTED_SALES_ORDER_LINE_AMMOUNT = (int) 123d;
	
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
		
    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderLineDao.CREATE_QUERY)).thenReturn(createPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderLineDao.READ_QUERY)).thenReturn(readPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderLineDao.GET_QUERY)).thenReturn(getPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderLineDao.UPDATE_QUERY)).thenReturn(updatePreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderLineDao.DELETE_QUERY)).thenReturn(deletePreparedStatementMock);
       
        when(sqlConnectionMock.prepareStatement(JdbcItemDao.GET_QUERY)).thenReturn(getItemPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcItemCategoryDao.GET_QUERY)).thenReturn(getItemCategoryPreparedStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcProductGroupDao.GET_QUERY)).thenReturn(getProductGroupPreparedStatementMock);
        
        
        when(readPreparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(getPreparedStatementMock.executeQuery()).thenReturn(resultSetMock);
       
        when(getItemPreparedStatementMock.executeQuery()).thenReturn(itemResultSetMock);
        when(getProductGroupPreparedStatementMock.executeQuery()).thenReturn(productGroupResultSetMock);
        when(getItemCategoryPreparedStatementMock.executeQuery()).thenReturn(itemCategoryResulSetMock);
        
        
        when(resultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(itemResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(itemCategoryResulSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(productGroupResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        
        when(resultSetMock.getString("id")).thenReturn(TEST_POSTED_SALES_ORDER_LINE_ID);
        when(resultSetMock.getInt("lineNumber")).thenReturn(TEST_POSTED_SALES_ORDER_LINE_LINE_NUMBER);
        when(resultSetMock.getString("item_id")).thenReturn(TEST_ITEM_ID);
        when(resultSetMock.getInt("quantity")).thenReturn(TEST_POSTED_SALES_ORDER_LINE_QUANTITY);
        when(resultSetMock.getFloat("price")).thenReturn(TEST__POSTED_SALES_ORDER_LINE_PRICE);
        when(resultSetMock.getInt("ammount")).thenReturn(TEST_POSTED_SALES_ORDER_LINE_AMMOUNT);
        
        when(productGroupResultSetMock.getString("id")).thenReturn(TEST_PRODUCT_GROUP_ID);
        when(productGroupResultSetMock.getString("description")).thenReturn(TEST_PRODUCT_GROUP_DESCRIPTION);
        when(productGroupResultSetMock.getString("itemCategory_id")).thenReturn(TEST_ITEM_CATEGORY_ID);
       
        when(itemCategoryResulSetMock.getString("id")).thenReturn(TEST_ITEM_CATEGORY_ID);
        when(itemCategoryResulSetMock.getString("name")).thenReturn(TEST_ITEM_CATEGORY_NAME);
        when(itemCategoryResulSetMock.getString("description")).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
       
        when(itemResultSetMock.getString("id")).thenReturn(TEST_ITEM_ID);
        when(itemResultSetMock.getString("name")).thenReturn(TEST_ITEM_NAME);
        when(itemResultSetMock.getString("description")).thenReturn(TEST_ITEM_DESCRIPTION);
        when(itemResultSetMock.getString("productGroup_id")).thenReturn(TEST_PRODUCT_GROUP_ID);
        when(itemResultSetMock.getString("itemCategory_id")).thenReturn(TEST_ITEM_CATEGORY_ID);
        when(itemResultSetMock.getFloat("purchasePrice")).thenReturn(TEST_PURCHASE_PRICE);
        when(itemResultSetMock.getFloat("salesPrice")).thenReturn(TEST_SALES_PRICE);
        
        when(postedSalesOrderLineMock.getId()).thenReturn(TEST_POSTED_SALES_ORDER_LINE_ID);
        when(postedSalesOrderLineMock.getLineNumber()).thenReturn(TEST_POSTED_SALES_ORDER_LINE_LINE_NUMBER);
        when(postedSalesOrderLineMock.getItem()).thenReturn(itemMock);
        when(postedSalesOrderLineMock.getQuantity()).thenReturn(TEST_POSTED_SALES_ORDER_LINE_QUANTITY);
        when(postedSalesOrderLineMock.getPrice()).thenReturn(TEST__POSTED_SALES_ORDER_LINE_PRICE);
        when(postedSalesOrderLineMock.getAmmount()).thenReturn(TEST_POSTED_SALES_ORDER_LINE_AMMOUNT);
        
        
        postedSalesOrderLineDaoMock = new JdbcPostedSalesOrderLineDao(sqlConnectionMock);
    }
	
	@Test
	public void testCreate() throws SQLException{
		postedSalesOrderLineDaoMock.create(postedSalesOrderLineMock);
		
		verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderLineDao.CREATE_QUERY);
		
		verify(createPreparedStatementMock).setString(1, postedSalesOrderLineMock.getId());
		verify(createPreparedStatementMock).setInt(2, postedSalesOrderLineMock.getLineNumber());
		verify(createPreparedStatementMock).setString(3, postedSalesOrderLineMock.getItem().getId());
		verify(createPreparedStatementMock).setInt(4, postedSalesOrderLineMock.getQuantity());
		verify(createPreparedStatementMock).setFloat(5, postedSalesOrderLineMock.getPrice());
		verify(createPreparedStatementMock).setInt(6, postedSalesOrderLineMock.getAmmount());
		
		verify(createPreparedStatementMock).executeUpdate();
	}
	
	@Test
	public void testRead() throws SQLException{
		List<PostedSalesOrderLine> list = postedSalesOrderLineDaoMock.read();
		
		verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderLineDao.READ_QUERY);
		verify(readPreparedStatementMock).executeQuery();
		verify(resultSetMock, times(2)).next();
		
		assertNotNull(list);
		
		PostedSalesOrderLine line = list.get(0);
		
		assertNotNull(line);
		
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_ID, line.getId());
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_LINE_NUMBER, line.getLineNumber());
		assertEquals(TEST_ITEM_ID, line.getItem().getId());
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_QUANTITY, line.getQuantity());
		assertEquals(TEST__POSTED_SALES_ORDER_LINE_PRICE, line.getPrice(), 0f);
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_AMMOUNT, line.getAmmount());
		
	}
	
	@Test
	public void testGet() throws SQLException{
		PostedSalesOrderLine line = postedSalesOrderLineDaoMock.get(TEST_POSTED_SALES_ORDER_LINE_ID);
		
		verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderLineDao.GET_QUERY);
		verify(getPreparedStatementMock).setString(1, TEST_POSTED_SALES_ORDER_LINE_ID);
		verify(getPreparedStatementMock).executeQuery();
		verify(resultSetMock).next();
		
		assertNotNull(line);
		
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_ID, line.getId());
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_LINE_NUMBER, line.getLineNumber());
		assertEquals(TEST_ITEM_ID, line.getItem().getId());
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_QUANTITY, line.getQuantity());
		assertEquals(TEST__POSTED_SALES_ORDER_LINE_PRICE, line.getPrice(), 0f);
		assertEquals(TEST_POSTED_SALES_ORDER_LINE_AMMOUNT, line.getAmmount());
		
	}
	
	@Test
	public void testUpdate() throws SQLException{
		PostedSalesOrderLine line = postedSalesOrderLineDaoMock.update(postedSalesOrderLineMock);
		
		verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderLineDao.UPDATE_QUERY);
		verify(updatePreparedStatementMock).setString(1, postedSalesOrderLineMock.getId());
		verify(updatePreparedStatementMock).setInt(2, postedSalesOrderLineMock.getLineNumber());
		verify(updatePreparedStatementMock).setString(3, postedSalesOrderLineMock.getItem().getId());
		verify(updatePreparedStatementMock).setInt(4, postedSalesOrderLineMock.getQuantity());
		verify(updatePreparedStatementMock).setFloat(5, postedSalesOrderLineMock.getPrice());
		verify(updatePreparedStatementMock).setInt(6, postedSalesOrderLineMock.getAmmount());
		verify(updatePreparedStatementMock).setString(7, postedSalesOrderLineMock.getId());
		
		verify(updatePreparedStatementMock).executeUpdate();
		
		assertNotNull(line);
	}
	
	@Test
	public void testRead_executeQueryError() throws SQLException{
		when(readPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
		
		List<PostedSalesOrderLine> list = postedSalesOrderLineDaoMock.read();
		
		assertNull(list);
		
	}
	
	@Test
	public void testGet_executeQueryError() throws SQLException{
		when(getPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
		
		PostedSalesOrderLine line = postedSalesOrderLineDaoMock.get(TEST_POSTED_SALES_ORDER_LINE_ID);
		
		assertNull(line);
	}
	
	@Test
	public void testUpdate_executeUpdateError() throws SQLException{
		when(updatePreparedStatementMock.executeUpdate()).thenThrow(new SQLException());
		
		PostedSalesOrderLine line = postedSalesOrderLineDaoMock.update(postedSalesOrderLineMock);
		
		assertNull(line);
	}
	
	
	@Test
	public void testDelete() throws SQLException{
		postedSalesOrderLineDaoMock.delete(postedSalesOrderLineMock);
		
		verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderLineDao.DELETE_QUERY);
		verify(deletePreparedStatementMock).setString(1, postedSalesOrderLineMock.getId());
		
		verify(deletePreparedStatementMock).executeUpdate();
		
	}	
}
