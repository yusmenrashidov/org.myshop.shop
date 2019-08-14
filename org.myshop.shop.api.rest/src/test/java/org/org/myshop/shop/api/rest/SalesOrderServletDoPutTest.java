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
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class SalesOrderServletDoPutTest {
	
	private final static String TEST_REQUEST_BODY = "test_request_body";
	
	private SalesOrderServlet salesOrderServlet;

	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<SalesOrder> deserializer;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private SalesOrderDao salesOrderDaoMock;
	
	@Mock
	private SalesOrder salesOrderMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(deserializer.deserialize(TEST_REQUEST_BODY)).thenReturn(salesOrderMock);
		
		salesOrderServlet = new SalesOrderServlet();
		
		salesOrderServlet.setRequestBodyReader(requestBodyReaderMock);
		salesOrderServlet.setDeserializer(deserializer);
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
	public void testJSONStringFailed() throws DeserializationException {
		when(deserializer.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		salesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
