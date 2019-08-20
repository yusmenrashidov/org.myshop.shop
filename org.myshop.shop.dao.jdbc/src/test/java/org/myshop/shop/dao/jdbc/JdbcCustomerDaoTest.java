package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.model.Customer;

public class JdbcCustomerDaoTest {

	   private static final String TEST_CUSTOMER_ID = "test_customer_id";
       private static final String TEST_CUSTOMER_NAME = "test_customer_name";
	   
	    @Mock
	    private Connection sqlConnectionMock;
	       
	    @Mock
	    private Customer customerMock;
	    
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
	    private JdbcCustomerDao customerDaoMock;

	    @Before
	    public void setup() throws SQLException {
	        MockitoAnnotations.initMocks(this);
	        
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.CREATE_QUERY)).thenReturn(createPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.READ_QUERY)).thenReturn(readPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.GET_QUERY)).thenReturn(getPreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.UPDATE_QUERY)).thenReturn(updatePreparedStatementMock);
	        when(sqlConnectionMock.prepareStatement(JdbcCustomerDao.DELETE_QUERY)).thenReturn(deletePreparedStatementMock);
	        
	        when(readPreparedStatementMock.executeQuery()).thenReturn(rsMock);
	        when(getPreparedStatementMock.executeQuery()).thenReturn(rsMock);
	        
	        when(rsMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
	        
	        when(customerMock.getId()).thenReturn(TEST_CUSTOMER_ID);
	        when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
	        
	        when(rsMock.getString("id")).thenReturn(TEST_CUSTOMER_ID);
	        when(rsMock.getString("name")).thenReturn(TEST_CUSTOMER_NAME);
	               
	        customerDaoMock = new JdbcCustomerDao(sqlConnectionMock);
	    }
	    
	    @Test
	    public void testCreate() throws SQLException {
	        customerDaoMock.create(customerMock);
	        
	        verify(sqlConnectionMock).prepareStatement(JdbcCustomerDao.CREATE_QUERY);
	        verify(createPreparedStatementMock).setString(1, customerMock.getId());
	        verify(createPreparedStatementMock).setString(2, customerMock.getName());
	      
	        verify(createPreparedStatementMock).executeUpdate();
	    }
	    
	    @Test
	    public void testRead() throws SQLException{
	    List<Customer>listMock = customerDaoMock.read();
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcCustomerDao.READ_QUERY);
	    	verify(readPreparedStatementMock).executeQuery();
	    	verify(rsMock, times(2)).next();
	    	
	    	assertNotNull(listMock);
	
	    	 customerMock = listMock.get(0);
	    	
	    	assertNotNull(customerMock);
	    	
	    	assertEquals(TEST_CUSTOMER_ID, customerMock.getId());
	    	assertEquals(TEST_CUSTOMER_NAME, customerMock.getName());
	    	
	    }
	    
	    @Test
	    public void testGet() throws SQLException{
	    	Customer customer = customerDaoMock.get(TEST_CUSTOMER_ID);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcCustomerDao.GET_QUERY);
	    	verify(getPreparedStatementMock).setString(1, TEST_CUSTOMER_ID);
	    	verify(getPreparedStatementMock).executeQuery();
	    	verify(rsMock).next();
	    	
	    	assertNotNull(customer);
	    	
	    	assertEquals(TEST_CUSTOMER_ID, customer.getId());
	    	assertEquals(TEST_CUSTOMER_NAME, customer.getName());
	    	
	     }
	    
	    @Test
	    public void testUpdate() throws SQLException{
	    	customerDaoMock.update(customerMock);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcCustomerDao.UPDATE_QUERY);
	    	verify(updatePreparedStatementMock).setString(1, customerMock.getId());
	    	verify(updatePreparedStatementMock).setString(2, customerMock.getName());
	    	verify(updatePreparedStatementMock).setString(3, customerMock.getId());
	    	
	    	verify(updatePreparedStatementMock).executeUpdate();
	    }
	    
	    @Test
	    public void testRead_executeQuerryError() throws SQLException{
	    	when(readPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
	    	
	    	List <Customer> customerList = customerDaoMock.read();
	    	
	    	assertNull(customerList);
	    }
	    
	    @Test
	    public void testGet_executeQuerryError() throws SQLException{
	    	when(getPreparedStatementMock.executeQuery()).thenThrow(new SQLException());
	    	
	    	Customer customer = customerDaoMock.get(TEST_CUSTOMER_ID);
	    	
	    	assertNull(customer);
	    }
	    
	    @Test
	    public void testUpdate_executeQuerryError() throws SQLException{
	    	when(updatePreparedStatementMock.executeUpdate()).thenThrow(new SQLException());
	    	
	    	Customer customer = customerDaoMock.update(customerMock);
	    	
	    	assertNull(customer);
	    }
	    
	    
	    @Test
	    public void testDelete() throws SQLException{
	    	customerDaoMock.delete(customerMock);
	    	
	    	verify(sqlConnectionMock).prepareStatement(JdbcCustomerDao.DELETE_QUERY);
	    	verify(deletePreparedStatementMock).setString(1, customerMock.getId());
	    	verify(deletePreparedStatementMock).executeUpdate();
	    	
	    }
	
}
