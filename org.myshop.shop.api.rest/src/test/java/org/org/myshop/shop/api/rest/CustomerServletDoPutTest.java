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
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class CustomerServletDoPutTest {

	private final String TEST_REQUEST_BODY = "test_request_body";
	
	private CustomerServlet customerServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<Customer> deserializerMock;
	
	@Mock
	private CustomerDao customerDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private Customer customerMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(customerMock);
		
		customerServlet = new CustomerServlet();
		customerServlet.setRequestBodyReader(requestBodyReaderMock);
		customerServlet.setDeserializer(deserializerMock);
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
	public void testJSONFailed() throws DeserializationException {
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		customerServlet.doPut(requestMock, responseMock);
		
		responseMock.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
}
