package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;

import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PurchaseOrderDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private PurchaseOrderServlet purchaseOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IPurchaseOrderDeserializer purchaseOrderDeserializerMock;
	
	@Mock
	private PurchaseOrderDao purchaseOrderDaoMock;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private PurchaseOrder purchaseOrderMock;
    
    @Before
    public void setup() throws IOException, PurchaseOrderDeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	purchaseOrderServlet = new PurchaseOrderServlet();
    	purchaseOrderServlet.setPurchaseOrderDao(purchaseOrderDaoMock);
    	purchaseOrderServlet.setPurchaseOrderDeserializer(purchaseOrderDeserializerMock);
    	purchaseOrderServlet.setRequestBodyReader(requestBodyReaderMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(purchaseOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(purchaseOrderMock);
    	when(purchaseOrderDaoMock.update(purchaseOrderMock)).thenReturn(purchaseOrderMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	purchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(purchaseOrderDaoMock.update(purchaseOrderMock)).thenReturn(null);
    	
    	purchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testDeserializerFailed() throws PurchaseOrderDeserializationException, IOException   {
    	when(purchaseOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new PurchaseOrderDeserializationException());
    	
    	purchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
