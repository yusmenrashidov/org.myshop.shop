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

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PurchaseOrderServletDoPutTest {
	
	private final String TEST_REQUEST_BODY = "test_request_body";
	
	private PurchaseOrderServlet purchaseOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<PurchaseOrder> deserializerMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PurchaseOrderDao purchaseOrderDaoMock;
	
	@Mock
	private PurchaseOrder purchaseOrderMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(purchaseOrderMock);
		
		purchaseOrderServlet = new PurchaseOrderServlet();
		purchaseOrderServlet.setRequestBodyReader(requestBodyReaderMock);
		purchaseOrderServlet.setDeserializer(deserializerMock);
		purchaseOrderServlet.setPurchaseOrderDao(purchaseOrderDaoMock);
	}
	
	@Test
	public void testSuccess() {
		purchaseOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException{
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		purchaseOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws DeserializationException {
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		purchaseOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
