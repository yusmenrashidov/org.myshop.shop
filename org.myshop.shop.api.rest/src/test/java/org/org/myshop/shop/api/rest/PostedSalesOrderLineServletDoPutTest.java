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

import org.myshop.shop.dao.PostedSalesOrderLineDao;
import org.myshop.shop.model.PostedSalesOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PostedSalesOrderLineServletDoPutTest {

private final String TEST_REQUEST_BODY = "test_request_body";
	
	private PostedSalesOrderLineServlet postedSalesOrderLineServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<PostedSalesOrderLine> deserializerMock;
	
	@Mock
	private PostedSalesOrderLineDao postedSalesOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedSalesOrderLine postedSalesOrderLineMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedSalesOrderLineMock);
		
		postedSalesOrderLineServlet = new PostedSalesOrderLineServlet();
		postedSalesOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
		postedSalesOrderLineServlet.setDeserializer(deserializerMock);
		postedSalesOrderLineServlet.setPostedSalesOrderLineDao(postedSalesOrderLineDaoMock);
	}
	
	@Test
	public void testSuccess() {
		postedSalesOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		postedSalesOrderLineServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONFailed() throws DeserializationException {
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		postedSalesOrderLineServlet.doPut(requestMock, responseMock);
		
		responseMock.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
