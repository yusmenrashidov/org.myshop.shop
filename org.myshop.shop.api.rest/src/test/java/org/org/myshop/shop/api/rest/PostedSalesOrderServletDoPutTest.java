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
import org.myshop.shop.dao.PostedSalesOrderDao;
import org.myshop.shop.model.PostedSalesOrder;
import org.org.myshop.shop.api.rest.servlet.exc.PostedSalesOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPostedSalesOrderDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PostedSalesOrderServletDoPutTest {

private final String TEST_REQUEST_BODY = "test_request_body";
	
	private PostedSalesOrderServlet postedSalesOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IPostedSalesOrderDeserializer postedSalesOrderDeserializerMock;
	
	@Mock
	private PostedSalesOrderDao postedSalesOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedSalesOrder postedSalesOrderMock;
	
	@Before
	public void setup() throws IOException, PostedSalesOrderDeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(postedSalesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedSalesOrderMock);
		
		postedSalesOrderServlet = new PostedSalesOrderServlet();
		postedSalesOrderServlet.setRequestBodyReader(requestBodyReaderMock);
		postedSalesOrderServlet.setPostedSalesOrderDeserializer(postedSalesOrderDeserializerMock);
		postedSalesOrderServlet.setPostedSalesOrderDao(postedSalesOrderDaoMock);
	}
	
	@Test
	public void testSuccess() {
		postedSalesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		postedSalesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws PostedSalesOrderDeserializationException {
		when(postedSalesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new PostedSalesOrderDeserializationException());
		
		postedSalesOrderServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
