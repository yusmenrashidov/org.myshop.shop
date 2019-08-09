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

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;

import org.org.myshop.shop.api.rest.servlet.exc.PostedPurchaseOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPostedPurchaseOrderDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PostedPurchaseOrderServletDoPutTest {

private final String TEST_REQUEST_BODY = "test_request_body";
	
	private PostedPurchaseOrderServlet postedPurchaseOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IPostedPurchaseOrderDeserializer postedPurchaseOrderDeserializerMock;
	
	@Mock
	private PostedPurchaseOrderDao postedPurchaseOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedPurchaseOrder postedPurchaseOrderMock;
	
	@Before
	public void setup() throws IOException, PostedPurchaseOrderDeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(postedPurchaseOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedPurchaseOrderMock);
		
		postedPurchaseOrderServlet = new PostedPurchaseOrderServlet();
		postedPurchaseOrderServlet.setRequestBodyReader(requestBodyReaderMock);
		postedPurchaseOrderServlet.setPostedPurchaseOrderDeserializer(postedPurchaseOrderDeserializerMock);
		postedPurchaseOrderServlet.setPostedPurchaseOrderDao(postedPurchaseOrderDaoMock);
	}
	
	@Test
	public void testSuccess() {
		postedPurchaseOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		postedPurchaseOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws PostedPurchaseOrderDeserializationException {
		when(postedPurchaseOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new PostedPurchaseOrderDeserializationException());
		
		postedPurchaseOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
