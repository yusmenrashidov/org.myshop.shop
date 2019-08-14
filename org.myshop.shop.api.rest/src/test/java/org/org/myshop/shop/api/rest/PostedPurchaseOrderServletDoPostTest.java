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
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PostedPurchaseOrderServletDoPostTest {

private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private PostedPurchaseOrderServlet postedPurchaseOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<PostedPurchaseOrder> deserializerMock;
	
	@Mock
	private PostedPurchaseOrderDao postedPurchaseOrderDaoMock;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private PostedPurchaseOrder postedPurchaseOrderMock;
    
    @Before
    public void setup() throws IOException, DeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	postedPurchaseOrderServlet = new PostedPurchaseOrderServlet();
    	postedPurchaseOrderServlet.setRequestBodyReader(requestBodyReaderMock);
    	postedPurchaseOrderServlet.setPostedPurchaseOrderDao(postedPurchaseOrderDaoMock);
    	postedPurchaseOrderServlet.setDeserializer(deserializerMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedPurchaseOrderMock);
    	when(postedPurchaseOrderDaoMock.update(postedPurchaseOrderMock)).thenReturn(postedPurchaseOrderMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	postedPurchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(postedPurchaseOrderDaoMock.update(postedPurchaseOrderMock)).thenReturn(null);
    	
    	postedPurchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws IOException, DeserializationException   {
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
    	
    	postedPurchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
