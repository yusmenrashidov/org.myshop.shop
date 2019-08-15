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

import org.myshop.shop.dao.PostedPurchaseOrderLineDao;
import org.myshop.shop.model.PostedPurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PostedPurchaseOrderLineServletDoPutTest {

private final String TEST_REQUEST_BODY = "test_request_body";
	
	private PostedPurchaseOrderLineServlet postedPurchaseOrderLineServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<PostedPurchaseOrderLine> deserializerMock;
	
	@Mock
	private PostedPurchaseOrderLineDao postedPurchaseOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedPurchaseOrderLine postedPurchaseOrderLineMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedPurchaseOrderLineMock);
		
		postedPurchaseOrderLineServlet = new PostedPurchaseOrderLineServlet();
		postedPurchaseOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
		postedPurchaseOrderLineServlet.setDeserializer(deserializerMock);
		postedPurchaseOrderLineServlet.setPostedPurchaseOrderLineDao(postedPurchaseOrderLineDaoMock);
	}
	
	@Test
	public void testSuccess() {
		postedPurchaseOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		postedPurchaseOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONFailed() throws DeserializationException {
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		postedPurchaseOrderLineServlet.doPut(requestMock, responseMock);
		
		responseMock.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
