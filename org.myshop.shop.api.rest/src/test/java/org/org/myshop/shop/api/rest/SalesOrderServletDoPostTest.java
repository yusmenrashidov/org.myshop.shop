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

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderDeserializer;

public class SalesOrderServletDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private SalesOrderServlet salesOrderServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private ISalesOrderDeserializer salesOrderDeserializerMock;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private SalesOrderDao salesOrderDaoMock;
    
    @Mock
    private SalesOrder salesOrderMock;
    
    @Before
    public void setup() throws SalesOrderDeserializationException, IOException {
    	MockitoAnnotations.initMocks(this);
    	
    	salesOrderServlet = new SalesOrderServlet();
    	salesOrderServlet.setSalesOrderDao(salesOrderDaoMock);
    	salesOrderServlet.setSalesOrderDeserializer(salesOrderDeserializerMock);
    	salesOrderServlet.setRequestBodyReader(requestBodyReaderMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(salesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(salesOrderMock);
    	when(salesOrderDaoMock.update(salesOrderMock)).thenReturn(salesOrderMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	salesOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(salesOrderDaoMock.update(salesOrderMock)).thenReturn(null);
    	
    	salesOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws SalesOrderDeserializationException, IOException   {
    	when(salesOrderDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new SalesOrderDeserializationException());
    	
    	salesOrderServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    
}
