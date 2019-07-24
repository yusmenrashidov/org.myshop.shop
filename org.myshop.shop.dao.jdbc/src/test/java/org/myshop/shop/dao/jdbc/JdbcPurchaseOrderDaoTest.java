package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;

import org.myshop.shop.model.Customer;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;
import org.myshop.shop.model.PurchaseOrder;


public class JdbcPurchaseOrderDaoTest {
	
	 private static final String TEST_PURCHASE_ORDER_ID = "test_customer_id";
     private static final String TEST_PURCHASE_ORDER_NUMBER = "test_customer_name";
	 private static final Date TEST_PURCHASE_ORDER_CREATED = new Date(0);  
     
	    @Mock
	    private Connection sqlConnectionMock;
	       
	    @Mock
	    private PurchaseOrder purchaseOrderMock;
	    
	    @Mock
	    private ResultSet rsMock;
	    
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
	    private JdbcPurchaseOrderDao purchaseOrderDaoMock;

	    @Before
	    public void setup() throws SQLException {
	        MockitoAnnotations.initMocks(this);
	        
	        when(sqlConnectionMock.prepareStatement(JdbcPurchaseOrderDao.CREATE_QUERY)).thenReturn(createPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPurchaseOrderDao.READ_QUERY)).thenReturn(readPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPurchaseOrderDao.GET_QUERY)).thenReturn(getPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPurchaseOrderDao.UPDATE_QUERY)).thenReturn(updatePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPurchaseOrderDao.DELETE_QUERY)).thenReturn(deletePreparedStatementMock);
	        
	        when(readPreparedStatementMock.executeQuery()).thenReturn(rsMock);
	        when(getPreparedStatementMock.executeQuery()).thenReturn(rsMock);
	        
	        when(rsMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        
	        when(purchaseOrderMock.getId()).thenReturn(TEST_PURCHASE_ORDER_ID);
	        when(purchaseOrderMock.getNumber()).thenReturn(TEST_PURCHASE_ORDER_NUMBER);
	        when(purchaseOrderMock.getCreated()).thenReturn(TEST_PURCHASE_ORDER_CREATED);
	        
	        when(rsMock.getString("id")).thenReturn(TEST_PURCHASE_ORDER_ID);
	        when(rsMock.getString("name")).thenReturn(TEST_PURCHASE_ORDER_NUMBER);
	        when(rsMock.getDate("created")).thenReturn((java.sql.Date) TEST_PURCHASE_ORDER_CREATED);
	               
	        purchaseOrderDaoMock = new JdbcPurchaseOrderDao(sqlConnectionMock);
	    }
	    
	    @Test
	    public void testCreate() throws SQLException {
	        purchaseOrderDaoMock.create(purchaseOrderMock);
	        
	        verify(sqlConnectionMock).prepareStatement(JdbcPurchaseOrderDao.CREATE_QUERY);
	        verify(createPreparedStatementMock).setString(1, purchaseOrderMock.getId());
	        verify(createPreparedStatementMock).setString(2, purchaseOrderMock.getNumber());
	        verify(createPreparedStatementMock).setDate(3, (java.sql.Date) purchaseOrderMock.getCreated());
	        
	        verify(createPreparedStatementMock).executeUpdate();
	    }
	    
	    @Test
	    public void testRead() throws SQLException{
	    List<PurchaseOrder>listMock = purchaseOrderDaoMock.read();
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPurchaseOrderDao.READ_QUERY);
	    	verify(readPreparedStatementMock).executeQuery();
	    	verify(rsMock, times(2)).next();
	    	
	    	assertNotNull(listMock);
	
	    	 purchaseOrderMock = listMock.get(0);
	    	
	    	assertNotNull(purchaseOrderMock);
	    	
	    	assertEquals(TEST_PURCHASE_ORDER_ID, purchaseOrderMock.getId());
	    	assertEquals(TEST_PURCHASE_ORDER_NUMBER, purchaseOrderMock.getNumber());
	    	assertEquals(TEST_PURCHASE_ORDER_CREATED, purchaseOrderMock.getCreated());
	    	
	    }
	    
	    @Test
	    public void testGet() throws SQLException{
	    	PurchaseOrder purchaseOrder = purchaseOrderDaoMock.get(TEST_PURCHASE_ORDER_ID);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPurchaseOrderDao.GET_QUERY);
	    	verify(getPreparedStatementMock).setString(1, TEST_PURCHASE_ORDER_ID);
	    	verify(getPreparedStatementMock).executeQuery();
	    	verify(rsMock).next();
	    	
	    	assertNotNull(purchaseOrder);
	    	
	    	assertEquals(TEST_PURCHASE_ORDER_ID, purchaseOrder.getId());
	    	assertEquals(TEST_PURCHASE_ORDER_NUMBER, purchaseOrder.getNumber());
	    	
	     }
	    
	    @Test
	    public void testUpdate() throws SQLException{
	    	purchaseOrderDaoMock.update(purchaseOrderMock);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPurchaseOrderDao.UPDATE_QUERY);
	    	verify(updatePreparedStatementMock).setString(1, purchaseOrderMock.getId());
	    	verify(updatePreparedStatementMock).setString(2, purchaseOrderMock.getNumber());
	    	verify(updatePreparedStatementMock).setDate(3, (java.sql.Date) purchaseOrderMock.getCreated());
	    	
	    	verify(updatePreparedStatementMock).executeUpdate();
	    }
	    
	    @Test
	    public void testDelete() throws SQLException{
	    	purchaseOrderDaoMock.delete(purchaseOrderMock);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPurchaseOrderDao.DELETE_QUERY);
	    	verify(deletePreparedStatementMock).setString(1, purchaseOrderMock.getId());
	    	verify(deletePreparedStatementMock).executeUpdate();
	    	
	    }
}
