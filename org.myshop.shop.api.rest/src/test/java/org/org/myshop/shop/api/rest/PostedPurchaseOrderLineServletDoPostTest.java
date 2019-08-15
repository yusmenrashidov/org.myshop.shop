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

public class PostedPurchaseOrderLineServletDoPostTest {

private static final String TEST_REQUEST_BODY = "test_request_body";
	
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
    	
    	postedPurchaseOrderLineServlet = new PostedPurchaseOrderLineServlet();
    	postedPurchaseOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
    	postedPurchaseOrderLineServlet.setPostedPurchaseOrderLineDao(postedPurchaseOrderLineDaoMock);
    	postedPurchaseOrderLineServlet.setDeserializer(deserializerMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(postedPurchaseOrderLineMock);
    	when(postedPurchaseOrderLineDaoMock.update(postedPurchaseOrderLineMock)).thenReturn(postedPurchaseOrderLineMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	postedPurchaseOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(postedPurchaseOrderLineDaoMock.update(postedPurchaseOrderLineMock)).thenReturn(null);
    	
    	postedPurchaseOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws IOException, DeserializationException {
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
    	
    	postedPurchaseOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
