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

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderDeserializer;

public class SalesOrderServletDoPutTest {
	
	private final static String TEST_REQUEST_BODY = "test_request_body";
	
	private SalesOrderServlet salesOrderServlet;

	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private ISalesOrderDeserializer salesOrderDeserializerMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private SalesOrderDao salesOrderDaoMock;
	
	@Mock
	private SalesOrder salesOrderMock;
	
	@Before
	public void setup() throws IOException, SalesOrderDeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(salesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(salesOrderMock);
		
		salesOrderServlet = new SalesOrderServlet();
		
		salesOrderServlet.setRequestBodyReader(requestBodyReaderMock);
		salesOrderServlet.setSalesOrderDeserializer(salesOrderDeserializerMock);
		salesOrderServlet.setSalesOrderDao(salesOrderDaoMock);
	}
	
	@Test
	public void testSuccess() {
		salesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		salesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws SalesOrderDeserializationException {
		when(salesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new SalesOrderDeserializationException());
		
		salesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
