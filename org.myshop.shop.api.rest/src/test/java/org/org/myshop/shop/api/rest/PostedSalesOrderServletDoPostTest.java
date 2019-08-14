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

public class PostedSalesOrderServletDoPostTest {

private static final String TEST_REQUEST_BODY = "test_request_body";
	
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
    	
    	postedSalesOrderServlet = new PostedSalesOrderServlet();
    	postedSalesOrderServlet.setRequestBodyReader(requestBodyReaderMock);
    	postedSalesOrderServlet.setPostedSalesOrderDao(postedSalesOrderDaoMock);
    	postedSalesOrderServlet.setPostedSalesOrderDeserializer(postedSalesOrderDeserializerMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(postedSalesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedSalesOrderMock);
    	when(postedSalesOrderDaoMock.update(postedSalesOrderMock)).thenReturn(postedSalesOrderMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	postedSalesOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(postedSalesOrderDaoMock.update(postedSalesOrderMock)).thenReturn(null);
    	
    	postedSalesOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws IOException, PostedSalesOrderDeserializationException   {
    	when(postedSalesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new PostedSalesOrderDeserializationException());
    	
    	postedSalesOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
