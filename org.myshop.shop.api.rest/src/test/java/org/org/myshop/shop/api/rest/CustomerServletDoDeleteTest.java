package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;

import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class CustomerServletDoDeleteTest {

	private static final String TEST_CUSTOMER_ID = "test_customer_id";
	
	private CustomerServlet customerServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private CustomerDao customerDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private Customer customerMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		customerServlet = new CustomerServlet();
		customerServlet.setCustomerDao(customerDaoMock);
		customerServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_CUSTOMER_ID);
		when(customerDaoMock.get(TEST_CUSTOMER_ID)).thenReturn(customerMock);
	}
	
	@Test
	public void testSuccess() {
		
		customerServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(customerDaoMock.get(TEST_CUSTOMER_ID)).thenReturn(null);
		
		customerServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
