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

public class PostedSalesOrderLineServletDoPostTest {

private static final String TEST_REQUEST_BODY = "test_request_body";
	
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
    	
    	postedSalesOrderLineServlet = new PostedSalesOrderLineServlet();
    	postedSalesOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
    	postedSalesOrderLineServlet.setPostedSalesOrderLineDao(postedSalesOrderLineDaoMock);
    	postedSalesOrderLineServlet.setDeserializer(deserializerMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedSalesOrderLineMock);
    	when(postedSalesOrderLineDaoMock.update(postedSalesOrderLineMock)).thenReturn(postedSalesOrderLineMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	postedSalesOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(postedSalesOrderLineDaoMock.update(postedSalesOrderLineMock)).thenReturn(null);
    	
    	postedSalesOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws IOException, DeserializationException {
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
    	
    	postedSalesOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
	
}
