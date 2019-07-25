package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.model.SalesOrder;
import org.myshop.shop.model.Customer;

public class JdbcSalesOrderDaoTest {

	private static final String TEST_SALES_ORDER_ID = "test_salesOrder_id";
	private static final Date TEST_SALES_ORDER_CREATED = new Date(0);
	private static final String TEST_SALES_ORDER_CUSTOMER_ID = "test_salesOrder_customer_id";
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
	private JdbcSalesOrderDao salesOrderDaoMock;
	
	@Mock
	private JdbcCustomerDao customerDaoMock;
	
	@Mock
	private SalesOrder salesOrderMock;
	
	@Mock
	private Customer customerMock;
	
	 @Before
	    public void setup() throws SQLException {
	        MockitoAnnotations.initMocks(this);
	        
	        when(sqlConnectionMock.prepareStatement(JdbcSalesOrderDao.CREATE_QUERY)).thenReturn(createPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcSalesOrderDao.READ_QUERY)).thenReturn(readPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcSalesOrderDao.GET_QUERY)).thenReturn(getPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcSalesOrderDao.UPDATE_QUERY)).thenReturn(updatePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcSalesOrderDao.DELETE_QUERY)).thenReturn(deletePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.GET_QUERY)).thenReturn(getCustomerPreparedStatementMock);
	        
	        when(readPreparedStatementMock.executeQuery()).thenReturn(resultSetMock);
	        when(getPreparedStatementMock.executeQuery()).thenReturn(resultSetMock);
	        when(getCustomerPreparedStatementMock.executeQuery()).thenReturn(customerResultSetMock);
	        
	        
	        when(resultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        when(customerResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        
	        when(customerResultSetMock.getString("id")).thenReturn(TEST_SALES_ORDER_CUSTOMER_ID);
	        when(customerResultSetMock.getString("name")).thenReturn(TEST_CUSTOMER_NAME);
	        
	        when(resultSetMock.getString("id")).thenReturn(TEST_SALES_ORDER_ID);
	        when(resultSetMock.getDate("created")).thenReturn(TEST_SALES_ORDER_CREATED);
	        when(resultSetMock.getString("customer_id")).thenReturn(TEST_SALES_ORDER_CUSTOMER_ID);
	        
	        when(salesOrderMock.getId()).thenReturn(TEST_SALES_ORDER_ID);
	        when(salesOrderMock.getCustomer()).thenReturn(customerMock);
	        when(salesOrderMock.getCreated()).thenReturn(TEST_SALES_ORDER_CREATED);
	        when(salesOrderMock.getCustomer().getId()).thenReturn(TEST_SALES_ORDER_CUSTOMER_ID);
	        
	        when(customerMock.getId()).thenReturn(TEST_SALES_ORDER_CUSTOMER_ID);
	        when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
	       
	        
	        salesOrderDaoMock = new JdbcSalesOrderDao(sqlConnectionMock);
	        customerDaoMock = new JdbcCustomerDao(sqlConnectionMock);
	    }
	
	 @Test
	 public void testCreate() throws SQLException{
		 salesOrderDaoMock.create(salesOrderMock);
		 
		    verify(sqlConnectionMock).prepareStatement(JdbcSalesOrderDao.CREATE_QUERY);
	        verify(createPreparedStatementMock).setString(1, salesOrderMock.getId());
	        verify(createPreparedStatementMock).setDate(2, (java.sql.Date) salesOrderMock.getCreated());
	        verify(createPreparedStatementMock).setString(3, salesOrderMock.getCustomer().getId());
	        
	        verify(createPreparedStatementMock).executeUpdate();
	 }
	 
	 @Test
	 public void testRead() throws SQLException{
		 	List<SalesOrder> salesOrderList = salesOrderDaoMock.read();
		 
		 	verify(sqlConnectionMock).prepareStatement(JdbcSalesOrderDao.READ_QUERY);
		 	verify(readPreparedStatementMock).executeQuery();
		 	
		 	verify(resultSetMock, times(2)).next();
		 	
		 	assertNotNull(salesOrderList);
		 	
		 	salesOrderMock = salesOrderList.get(0);
		 	
		 	assertEquals(TEST_SALES_ORDER_ID, salesOrderMock.getId());
		 	assertEquals(TEST_SALES_ORDER_CREATED, salesOrderMock.getCreated());
		 	assertEquals(TEST_SALES_ORDER_CUSTOMER_ID, salesOrderMock.getCustomer().getId());
	 }
	 
	 @Test
	 public void testGet() throws SQLException{
		 
		 SalesOrder salesOrder = salesOrderDaoMock.get(TEST_SALES_ORDER_ID);
		 
		 verify(sqlConnectionMock).prepareStatement(JdbcSalesOrderDao.GET_QUERY);
		 verify(getPreparedStatementMock).executeQuery();
		 
		 verify(resultSetMock).next();
		 
		 assertNotNull(salesOrder);
		 
			assertEquals(TEST_SALES_ORDER_ID, salesOrder.getId());
		 	assertEquals(TEST_SALES_ORDER_CREATED, salesOrder.getCreated());
		 	assertEquals(TEST_SALES_ORDER_CUSTOMER_ID, salesOrder.getCustomer().getId());
	 }
	 
	 @Test
	 public void testUpdate() throws SQLException{
		 
		 SalesOrder salesOrder = salesOrderDaoMock.update(salesOrderMock);
		 
		 verify(sqlConnectionMock).prepareStatement(JdbcSalesOrderDao.UPDATE_QUERY);
		 
		 verify(updatePreparedStatementMock).setString(1, salesOrderMock.getId());
		 verify(updatePreparedStatementMock).setDate(2,(java.sql.Date) salesOrder.getCreated());
		 verify(updatePreparedStatementMock).setString(3, salesOrderMock.getCustomer().getId());
		 verify(updatePreparedStatementMock).setString(4, salesOrderMock.getId());
		 
		 verify(updatePreparedStatementMock).executeUpdate();
		 
		 assertNotNull(salesOrder);
	}
	 
	 @Test
	 public void testRead_executeQueryError() throws SQLException{
		 
		 when(readPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
		 
		 List<SalesOrder> salesOrderList = salesOrderDaoMock.read();
		 
		 assertNull(salesOrderList);
	}
	 
	 @Test
	 public void testGet_executeQueryError() throws SQLException{
		 
		 when(getPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
		 
		 salesOrderMock = salesOrderDaoMock.get(TEST_SALES_ORDER_ID);
		 
		 assertNull(salesOrderMock);
	 }
	 
	 @Test
	 public void testUpdate_executeQueryError() throws SQLException{
		 
		 when(updatePreparedStatementMock.executeUpdate()).thenThrow(new SQLException());
		 
		 salesOrderMock = salesOrderDaoMock.update(salesOrderMock);
		 
		 assertNull(salesOrderMock);
	 }
	 
	 @Test
	 public void testDelete() throws SQLException{
		
		 salesOrderDaoMock.delete(salesOrderMock);

		 verify(sqlConnectionMock).prepareStatement(JdbcSalesOrderDao.DELETE_QUERY);
		 
		 verify(deletePreparedStatementMock).executeUpdate();
		 
	 }
	 
}
