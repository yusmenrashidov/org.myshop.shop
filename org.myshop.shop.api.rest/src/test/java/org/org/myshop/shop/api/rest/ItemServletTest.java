package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemServletTest {
    
    private static final String TEST_REQUEST_BODY = "test_request_body";

    private ItemServlet itemServlet;
    
    @Mock
    private IRequestBodyReader requestBodyReaderMock;
    
    @Mock
    private IItemDeserializer itemDeserializerMock;
    
    @Mock
    private ItemDao itemDaoMock;
    
    @Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private Item itemMock;
    
    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        
        when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
        
        when(itemDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(itemMock);
        
        itemServlet = new ItemServlet();
        itemServlet.setRequestBodyReader(requestBodyReaderMock);
        itemServlet.setItemDeserializer(itemDeserializerMock);
        itemServlet.setItemDao(itemDaoMock);
    }
    
    @Test
    public void testPut() {
        itemServlet.doPut(requestMock, responseMock);
        
        verify(itemDaoMock).create(itemMock);
        
        verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
    
    @Test
    public void testReadBodyFailed() throws IOException{
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
    	
    	itemServlet.doPut(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
    }
    
    @Test
    public void testDeserializeJSONStringFailed() throws IOException{
    	 
    	when(itemDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new IOException());
    	
    	itemServlet.doPut(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
    }
}

