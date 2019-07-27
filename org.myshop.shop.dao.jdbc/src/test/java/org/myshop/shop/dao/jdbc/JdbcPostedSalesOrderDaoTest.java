package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.model.Customer;
import org.myshop.shop.model.PostedSalesOrder;

public class JdbcPostedSalesOrderDaoTest {

	private static final String TEST_POSTED_SALES_ORDER_ID = "test_postedSalesOrder_id";
	private static final Date TEST_POSTED_SALES_ORDER_CREATED = new Date(0);
	private static final String TEST_POSTED_SALES_ORDER_CUSTOMER_ID = "test_postedSalesOrder_customer_id";
	private static final String TEST_CUSTOMER_NAME = "test_customer_name";
	
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
	private PreparedStatement getCustomerPreparedStatementMock;

	@Mock
	private ResultSet resultSetMock;
	
	@Mock
	private ResultSet customerResultSetMock;
	
	@Mock
	private JdbcPostedSalesOrderDao postedSalesOrderDaoMock;
	
	@Mock
	private JdbcCustomerDao customerDaoMock;
	
	@Mock
	private PostedSalesOrder postedSalesOrderMock;
	
	@Mock
	private Customer customerMock;
	
	 @Before
	    public void setup() throws SQLException {
	        MockitoAnnotations.initMocks(this);
	        
	        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderDao.CREATE_QUERY)).thenReturn(createPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderDao.READ_QUERY)).thenReturn(readPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderDao.GET_QUERY)).thenReturn(getPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderDao.UPDATE_QUERY)).thenReturn(updatePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcPostedSalesOrderDao.DELETE_QUERY)).thenReturn(deletePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.GET_QUERY)).thenReturn(getCustomerPreparedStatementMock);
	        
	        when(readPreparedStatementMock.executeQuery()).thenReturn(resultSetMock);
	        when(getPreparedStatementMock.executeQuery()).thenReturn(resultSetMock);
	        when(getCustomerPreparedStatementMock.executeQuery()).thenReturn(customerResultSetMock);
	        
	        
	        when(resultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        when(customerResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        
	        when(customerResultSetMock.getString("id")).thenReturn(TEST_POSTED_SALES_ORDER_CUSTOMER_ID);
	        when(customerResultSetMock.getString("name")).thenReturn(TEST_CUSTOMER_NAME);
	        
	        when(resultSetMock.getString("id")).thenReturn(TEST_POSTED_SALES_ORDER_ID);
	        when(resultSetMock.getDate("created")).thenReturn(TEST_POSTED_SALES_ORDER_CREATED);
	        when(resultSetMock.getString("customer_id")).thenReturn(TEST_POSTED_SALES_ORDER_CUSTOMER_ID);
	        
	        when(postedSalesOrderMock.getId()).thenReturn(TEST_POSTED_SALES_ORDER_ID);
	        when(postedSalesOrderMock.getCustomer()).thenReturn(customerMock);
	        when(postedSalesOrderMock.getCreated()).thenReturn(TEST_POSTED_SALES_ORDER_CREATED);
	        when(postedSalesOrderMock.getCustomer().getId()).thenReturn(TEST_POSTED_SALES_ORDER_CUSTOMER_ID);
	        
	        when(customerMock.getId()).thenReturn(TEST_POSTED_SALES_ORDER_CUSTOMER_ID);
	        when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
	       
	        
	        postedSalesOrderDaoMock = new JdbcPostedSalesOrderDao(sqlConnectionMock);
	        customerDaoMock = new JdbcCustomerDao(sqlConnectionMock);
	    }
	
	 @Test
	 public void testCreate() throws SQLException{
		 postedSalesOrderDaoMock.create(postedSalesOrderMock);
		 
		    verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderDao.CREATE_QUERY);
	        verify(createPreparedStatementMock).setString(1, postedSalesOrderMock.getId());
	        verify(createPreparedStatementMock).setDate(2, (java.sql.Date) postedSalesOrderMock.getCreated());
	        verify(createPreparedStatementMock).setString(3, postedSalesOrderMock.getCustomer().getId());
	        
	        verify(createPreparedStatementMock).executeUpdate();
	 }
	 
	 @Test
	 public void testRead() throws SQLException{
		 	List<PostedSalesOrder> postedSalesOrderList = postedSalesOrderDaoMock.read();
		 
		 	verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderDao.READ_QUERY);
		 	verify(readPreparedStatementMock).executeQuery();
		 	
		 	verify(resultSetMock, times(2)).next();
		 	
		 	assertNotNull(postedSalesOrderList);
		 	
		 	postedSalesOrderMock = postedSalesOrderList.get(0);
		 	
		 	assertEquals(TEST_POSTED_SALES_ORDER_ID, postedSalesOrderMock.getId());
		 	assertEquals(TEST_POSTED_SALES_ORDER_CREATED, postedSalesOrderMock.getCreated());
		 	assertEquals(TEST_POSTED_SALES_ORDER_CUSTOMER_ID, postedSalesOrderMock.getCustomer().getId());
	 }
	 
	 @Test
	 public void testGet() throws SQLException{
		 
		 PostedSalesOrder postedSalesOrder = postedSalesOrderDaoMock.get(TEST_POSTED_SALES_ORDER_ID);
		 
		 verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderDao.GET_QUERY);
		 verify(getPreparedStatementMock).executeQuery();
		 
		 verify(resultSetMock).next();
		 
		 assertNotNull(postedSalesOrder);
		 
			assertEquals(TEST_POSTED_SALES_ORDER_ID, postedSalesOrder.getId());
		 	assertEquals(TEST_POSTED_SALES_ORDER_CREATED, postedSalesOrder.getCreated());
		 	assertEquals(TEST_POSTED_SALES_ORDER_CUSTOMER_ID, postedSalesOrder.getCustomer().getId());
	 }
	 
	 @Test
	 public void testUpdate() throws SQLException{
		 
		 PostedSalesOrder postedSalesOrder= postedSalesOrderDaoMock.update(postedSalesOrderMock);
		 
		 verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderDao.UPDATE_QUERY);
		 
		 verify(updatePreparedStatementMock).setString(1, postedSalesOrderMock.getId());
		 verify(updatePreparedStatementMock).setDate(2,(java.sql.Date) postedSalesOrderMock.getCreated());
		 verify(updatePreparedStatementMock).setString(3, postedSalesOrderMock.getCustomer().getId());
		 verify(updatePreparedStatementMock).setString(4, postedSalesOrderMock.getId());
		 
		 verify(updatePreparedStatementMock).executeUpdate();
		 
		 assertNotNull(postedSalesOrder);
	}
	 
	 @Test
	 public void testRead_executeQueryError() throws SQLException{
		 
		 when(readPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
		 
		 List<PostedSalesOrder> postedSalesOrderList = postedSalesOrderDaoMock.read();
		 
		 assertNull(postedSalesOrderList);
	}
	 
	 @Test
	 public void testGet_executeQueryError() throws SQLException{
		 
		 when(getPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
		 
		 postedSalesOrderMock = postedSalesOrderDaoMock.get(TEST_POSTED_SALES_ORDER_ID);
		 
		 assertNull(postedSalesOrderMock);
	 }
	 
	 @Test
	 public void testUpdate_executeQueryError() throws SQLException{
		 
		 when(updatePreparedStatementMock.executeUpdate()).thenThrow(new SQLException());
		 
		 postedSalesOrderMock = postedSalesOrderDaoMock.update(postedSalesOrderMock);
		 
		 assertNull(postedSalesOrderMock);
	 }
	 
	 @Test
	 public void testDelete() throws SQLException{
		
		 postedSalesOrderDaoMock.delete(postedSalesOrderMock);

		 verify(sqlConnectionMock).prepareStatement(JdbcPostedSalesOrderDao.DELETE_QUERY);
		 
		 verify(deletePreparedStatementMock).executeUpdate();
		 
	 }
	
}
