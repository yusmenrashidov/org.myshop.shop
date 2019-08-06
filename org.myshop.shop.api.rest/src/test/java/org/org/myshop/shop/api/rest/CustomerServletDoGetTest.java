package org.org.myshop.shop.api.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.util.ICustomerSerializer;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class CustomerServletDoGetTest {

	private static final String TEST_JSON_STRING = "{}";
	
	private CustomerServlet customerServlet;
	
	@Mock
	private ICustomerSerializer customerSerializerMock;
	
	@Mock
	private CustomerDao customerDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<Customer> customerListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		customerServlet = new CustomerServlet();
		customerServlet.setCustomerDao(customerDaoMock);
		customerServlet.setCustomerSerializer(customerSerializerMock);
		
		when(customerDaoMock.read()).thenReturn(customerListMock);
		when(customerSerializerMock.serializerList(customerListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		customerServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(customerDaoMock.read()).thenReturn(Collections.<Customer>emptyList());
		
		customerServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(customerDaoMock.read()).thenReturn(null);
		
		customerServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
}
