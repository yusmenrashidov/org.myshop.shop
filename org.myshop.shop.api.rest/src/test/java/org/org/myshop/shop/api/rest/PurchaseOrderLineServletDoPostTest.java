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

import org.myshop.shop.dao.PurchaseOrderLineDao;
import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderLineDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderLineDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PurchaseOrderLineServletDoPostTest {

private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private PurchaseOrderLineServlet purchaseOrderLineServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IPurchaseOrderLineDeserializer purchaseOrderLineDeserializerMock;
	
	@Mock
	private PurchaseOrderLineDao purchaseOrderLineDaoMock;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private PurchaseOrderLine purchaseOrderLineMock;
    
    @Before
    public void setup() throws IOException, PurchaseOrderLineDeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	purchaseOrderLineServlet = new PurchaseOrderLineServlet();
    	purchaseOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
    	purchaseOrderLineServlet.setPurchaseOrderLineDao(purchaseOrderLineDaoMock);
    	purchaseOrderLineServlet.setPurchaseOrderLineDeserializer(purchaseOrderLineDeserializerMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(purchaseOrderLineDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(purchaseOrderLineMock);
    	when(purchaseOrderLineDaoMock.update(purchaseOrderLineMock)).thenReturn(purchaseOrderLineMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	purchaseOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(purchaseOrderLineDaoMock.update(purchaseOrderLineMock)).thenReturn(null);
    	
    	purchaseOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws PurchaseOrderLineDeserializationException, IOException   {
    	when(purchaseOrderLineDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new PurchaseOrderLineDeserializationException());
    	
    	purchaseOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
