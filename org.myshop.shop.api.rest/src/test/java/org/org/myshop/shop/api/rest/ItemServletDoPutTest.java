package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemServletDoPutTest {
    
    private static final String TEST_REQUEST_BODY = "test_request_body";

    private ItemServlet itemServlet;
    
    @Mock
    private IRequestBodyReader requestBodyReaderMock;
    
    @Mock
    private IDeserializer<Item> deserializerMock;
    
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
    public void setup() throws IOException, DeserializationException {
        MockitoAnnotations.initMocks(this);
        
        when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
        
        when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(itemMock);
        
        itemServlet = new ItemServlet();
        itemServlet.setRequestBodyReader(requestBodyReaderMock);
        itemServlet.setDeserializer(deserializerMock);
        itemServlet.setItemDao(itemDaoMock);
    }
    
    @Test
    public void testReadBodyFailed() throws IOException{
        when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
        
        itemServlet.doPut(requestMock, responseMock);
        
        verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testDeserializeJSONStringFailed() throws IOException, DeserializationException{
        when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
        
        itemServlet.doPut(requestMock, responseMock);
        
        verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testSuccess() throws IOException{
        itemServlet.doPut(requestMock, responseMock);
        
        verify(itemDaoMock).create(itemMock);
        verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}

