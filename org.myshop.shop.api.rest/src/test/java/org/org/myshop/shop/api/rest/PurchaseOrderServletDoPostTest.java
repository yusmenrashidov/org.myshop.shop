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
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class PurchaseOrderServletDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private PurchaseOrderServlet purchaseOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<PurchaseOrder> deserializerMock;
	
	@Mock
	private PurchaseOrderDao purchaseOrderDaoMock;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private PurchaseOrder purchaseOrderMock;
    
    @Before
    public void setup() throws IOException, DeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	purchaseOrderServlet = new PurchaseOrderServlet();
    	purchaseOrderServlet.setPurchaseOrderDao(purchaseOrderDaoMock);
    	purchaseOrderServlet.setDeserializer(deserializerMock);
    	purchaseOrderServlet.setRequestBodyReader(requestBodyReaderMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(purchaseOrderMock);
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
    public void testDeserializerFailed() throws IOException, DeserializationException   {
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
    	
    	purchaseOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
