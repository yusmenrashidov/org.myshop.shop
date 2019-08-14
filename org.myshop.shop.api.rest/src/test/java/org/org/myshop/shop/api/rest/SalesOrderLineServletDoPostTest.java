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
import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderLineDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderLineDeserializer;

public class SalesOrderLineServletDoPostTest {

private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private SalesOrderLineServlet salesOrderLineServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private ISalesOrderLineDeserializer salesOrderLineDeserializerMock;
	
	@Mock
	private SalesOrderLineDao salesOrderLineDaoMock;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private SalesOrderLine salesOrderLineMock;
    
    @Before
    public void setup() throws IOException, SalesOrderLineDeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	salesOrderLineServlet = new SalesOrderLineServlet();
    	salesOrderLineServlet.setRequestBodyReader(requestBodyReaderMock);
    	salesOrderLineServlet.setSalesOrderLineDao(salesOrderLineDaoMock);
    	salesOrderLineServlet.setSalesOrderLineDeserializer(salesOrderLineDeserializerMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(salesOrderLineDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(salesOrderLineMock);
    	when(salesOrderLineDaoMock.update(salesOrderLineMock)).thenReturn(salesOrderLineMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	salesOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(salesOrderLineDaoMock.update(salesOrderLineMock)).thenReturn(null);
    	
    	salesOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    @Test
    public void testDeserializerFailed() throws IOException, SalesOrderLineDeserializationException   {
    	when(salesOrderLineDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new SalesOrderLineDeserializationException());
    	
    	salesOrderLineServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
