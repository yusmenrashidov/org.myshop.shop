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

import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class SalesOrderLineServletDoPutTest {

private final String TEST_REQUEST_BODY = "test_request_body";
	
	private SalesOrderLineServlet salesOrderLineServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<SalesOrderLine> deserializer;
	
	@Mock
	private SalesOrderLineDao salesOrderLinDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private SalesOrderLine salesOrderLinMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(deserializer.deserialize(TEST_REQUEST_BODY)).thenReturn(salesOrderLinMock);
		
		salesOrderLineServlet = new SalesOrderLineServlet();
		salesOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
		salesOrderLineServlet.setDeserializer(deserializer);
		salesOrderLineServlet.setSalesOrderLineDao(salesOrderLinDaoMock);
	}
	
	@Test
	public void testSuccess() {
		salesOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		salesOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws DeserializationException {
		when(deserializer.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		salesOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
