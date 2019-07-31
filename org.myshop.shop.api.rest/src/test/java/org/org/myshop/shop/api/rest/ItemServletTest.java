package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;
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
    
    @Mock
    private List<Item> itemListMock;
    
    @Before
    public void setup() throws IOException, ItemDeserializationException {
        MockitoAnnotations.initMocks(this);
        
        when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
        
        when(itemDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(itemMock);
        
        itemServlet = new ItemServlet();
        itemServlet.setRequestBodyReader(requestBodyReaderMock);
        itemServlet.setItemDeserializer(itemDeserializerMock);
        itemServlet.setItemDao(itemDaoMock);
    }
    
    @Test
    public void testPut() throws IOException{
        itemServlet.doPut(requestMock, responseMock);
        
        verify(itemDaoMock).create(itemMock);
        
        verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
    
    @Test
    public void testGet() throws IOException, NullPointerException{
    	when(itemDaoMock.read()).thenReturn(itemListMock);
    	
    	itemServlet.doGet(requestMock, responseMock);
    	
    	verify(itemDaoMock).read();
    	
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testGetFailed() throws IOException{
    	when(itemDaoMock.read()).thenReturn(null);
    	
    	itemServlet.doGet(requestMock, responseMock);
    	
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testEmptyList() throws IOException{
    	when(itemDaoMock.read()).thenReturn(Collections.<Item>emptyList());
    	
    	itemServlet.doGet(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
    
    @Test
    public void testReadBodyFailed() throws IOException{
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
    	
    	itemServlet.doPut(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testDeserializeJSONStringFailed() throws IOException, ItemDeserializationException{
    	 
    	when(itemDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new ItemDeserializationException());
    	
    	itemServlet.doPut(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    
}

