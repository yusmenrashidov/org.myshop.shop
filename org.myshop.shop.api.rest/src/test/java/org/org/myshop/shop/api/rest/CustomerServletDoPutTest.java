package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.exc.CustomerDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.ICustomerDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class CustomerServletDoPutTest {

	private final String TEST_REQUEST_BODY = "test_request_body";
	
	private CustomerServlet customerServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private ICustomerDeserializer customerDeserializerMock;
	
	@Mock
	private CustomerDao customerDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private Customer customerMock;
	
	@Before
	public void setup() throws IOException, CustomerDeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(customerDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(customerMock);
		
		customerServlet = new CustomerServlet();
		customerServlet.setRequestBodyReader(requestBodyReaderMock);
		customerServlet.setCustomerDeserializer(customerDeserializerMock);
		customerServlet.setCustomerDao(customerDaoMock);
	}
	
	@Test
	public void testSuccess() {
		customerServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		customerServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws CustomerDeserializationException {
		when(customerDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new CustomerDeserializationException());
		
		customerServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
}
