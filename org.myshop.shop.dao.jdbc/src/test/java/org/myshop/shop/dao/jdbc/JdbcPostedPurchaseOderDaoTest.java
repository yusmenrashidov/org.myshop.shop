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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.myshop.shop.model.PostedPurchaseOrder;

public class JdbcPostedPurchaseOderDaoTest {

	 private static final String TEST_POSTED_PURCHASE_ORDER_ID = "test_postedPurchaseOrder_id";
     private static final String TEST_POSTED_PURCHASE_ORDER_NUMBER = "test_postedPurchaseOrder_number";
	 private static final Date TEST_POSTED_PURCHASE_ORDER_CREATED = new Date(0);  
     
	    @Mock
	    private Connection sqlConnectionMock;
	       
	    @Mock
	    private PostedPurchaseOrder postedPurchaseOrderMock;
	    
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
	    private JdbcPostedPurchaseOrderDao postedPurchaseOrderDaoMock;

	    @Before
	    public void setup() throws SQLException {
	        MockitoAnnotations.initMocks(this);
	        
	        when(sqlConnectionMock.prepareStatement(JdbcPostedPurchaseOrderDao.CREATE_QUERY)).thenReturn(createPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedPurchaseOrderDao.READ_QUERY)).thenReturn(readPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedPurchaseOrderDao.GET_QUERY)).thenReturn(getPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedPurchaseOrderDao.UPDATE_QUERY)).thenReturn(updatePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedPurchaseOrderDao.DELETE_QUERY)).thenReturn(deletePreparedStatementMock);
	        
	        when(readPreparedStatementMock.executeQuery()).thenReturn(rsMock);
	        when(getPreparedStatementMock.executeQuery()).thenReturn(rsMock);
	        
	        when(rsMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        
	        when(postedPurchaseOrderMock.getId()).thenReturn(TEST_POSTED_PURCHASE_ORDER_NUMBER);
	        when(postedPurchaseOrderMock.getNumber()).thenReturn(TEST_POSTED_PURCHASE_ORDER_ID);
	        when(postedPurchaseOrderMock.getCreated()).thenReturn(TEST_POSTED_PURCHASE_ORDER_CREATED);
	        
	        when(rsMock.getString("id")).thenReturn(TEST_POSTED_PURCHASE_ORDER_NUMBER);
	        when(rsMock.getString("number")).thenReturn(TEST_POSTED_PURCHASE_ORDER_ID);
	        when(rsMock.getDate("created")).thenReturn(TEST_POSTED_PURCHASE_ORDER_CREATED);
	               
	        postedPurchaseOrderDaoMock = new JdbcPostedPurchaseOrderDao(sqlConnectionMock);
	    }
	    
	    @Test
	    public void testCreate() throws SQLException {
	    	postedPurchaseOrderDaoMock.create(postedPurchaseOrderMock);
	        
	        verify(sqlConnectionMock).prepareStatement(JdbcPostedPurchaseOrderDao.CREATE_QUERY);
	        verify(createPreparedStatementMock).setString(1, postedPurchaseOrderMock.getId());
	        verify(createPreparedStatementMock).setString(2, postedPurchaseOrderMock.getNumber());
	        verify(createPreparedStatementMock).setDate(3, (java.sql.Date) postedPurchaseOrderMock.getCreated());
	        
	        verify(createPreparedStatementMock).executeUpdate();
	    }
	    
	    @Test
	    public void testRead() throws SQLException{
	    List<PostedPurchaseOrder>listMock = postedPurchaseOrderDaoMock.read();
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPostedPurchaseOrderDao.READ_QUERY);
	    	verify(readPreparedStatementMock).executeQuery();
	    	verify(rsMock, times(2)).next();
	    	
	    	assertNotNull(listMock);
	
	    	postedPurchaseOrderMock = listMock.get(0);
	    	
	    	assertNotNull(listMock);
	    	assertNotNull(postedPurchaseOrderMock);
	    	
	    	assertEquals(TEST_POSTED_PURCHASE_ORDER_NUMBER, postedPurchaseOrderMock.getId());
	    	assertEquals(TEST_POSTED_PURCHASE_ORDER_ID, postedPurchaseOrderMock.getNumber());
	    	assertEquals(TEST_POSTED_PURCHASE_ORDER_CREATED, postedPurchaseOrderMock.getCreated());
	    	
	    }
	    
	    @Test
	    public void testGet() throws SQLException{
	    	PostedPurchaseOrder postedPurchaseOrder = postedPurchaseOrderDaoMock.get(TEST_POSTED_PURCHASE_ORDER_NUMBER);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPostedPurchaseOrderDao.GET_QUERY);
	    	verify(getPreparedStatementMock).setString(1, TEST_POSTED_PURCHASE_ORDER_NUMBER);
	    	verify(getPreparedStatementMock).executeQuery();
	    	verify(rsMock).next();
	    	
	    	assertNotNull(postedPurchaseOrder);
	    	
	    	assertEquals(TEST_POSTED_PURCHASE_ORDER_NUMBER, postedPurchaseOrder.getId());
	    	assertEquals(TEST_POSTED_PURCHASE_ORDER_ID, postedPurchaseOrder.getNumber());
	    	assertEquals(TEST_POSTED_PURCHASE_ORDER_CREATED, postedPurchaseOrder.getCreated());
	     }
	    
	    @Test
	    public void testUpdate() throws SQLException{
	    	postedPurchaseOrderDaoMock.update(postedPurchaseOrderMock);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPostedPurchaseOrderDao.UPDATE_QUERY);
	    	verify(updatePreparedStatementMock).setString(1, postedPurchaseOrderMock.getId());
	    	verify(updatePreparedStatementMock).setString(2, postedPurchaseOrderMock.getNumber());
	    	verify(updatePreparedStatementMock).setDate(3, (java.sql.Date) postedPurchaseOrderMock.getCreated());
	    	
	    	verify(updatePreparedStatementMock).executeUpdate();
	    }
	    
	    @Test
	    public void testGet_executeQueryError() throws SQLException{
	    	when(getPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
	    	
	    	PostedPurchaseOrder postedPurchaseOrder = postedPurchaseOrderDaoMock.get(TEST_POSTED_PURCHASE_ORDER_NUMBER);
	    	
	    	assertNull(postedPurchaseOrder);
	    }
	    
	    @Test
	    public void testRead_executeQueryError() throws SQLException{
	    	when(readPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
	    	
	    	List<PostedPurchaseOrder> postedPurchaseOrderList = postedPurchaseOrderDaoMock.read();
	    	
	    	assertNull(postedPurchaseOrderList);
	    }
	    
	    @Test
	    public void testUpdate_executeQueryError() throws SQLException{
	    	when(updatePreparedStatementMock.executeUpdate()).thenThrow(new SQLException());
	    	
	    	PostedPurchaseOrder purchaseOrder = postedPurchaseOrderDaoMock.update(postedPurchaseOrderMock);
	    	
	    	assertNull(purchaseOrder);
	    }
	    
	    @Test
	    public void testDelete() throws SQLException{
	    	postedPurchaseOrderDaoMock.delete(postedPurchaseOrderMock);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcPostedPurchaseOrderDao.DELETE_QUERY);
	    	verify(deletePreparedStatementMock).setString(1, postedPurchaseOrderMock.getId());
	    	verify(deletePreparedStatementMock).executeUpdate();
	    	
	    }
}
