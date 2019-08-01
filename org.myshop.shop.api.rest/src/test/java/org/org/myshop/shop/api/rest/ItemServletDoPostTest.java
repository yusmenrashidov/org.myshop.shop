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

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemServletDoPostTest {
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
	    public void setup() throws IOException, ItemDeserializationException {
	    	MockitoAnnotations.initMocks(this);
	    	
	    	itemServlet = new ItemServlet();
	    	itemServlet.setItemDao(itemDaoMock);
	    	itemServlet.setItemDeserializer(itemDeserializerMock);
	    	itemServlet.setRequestBodyReader(requestBodyReaderMock);
	    	
	    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
	    	when(itemDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(itemMock);
	    	when(itemDaoMock.update(itemMock)).thenReturn(itemMock);
	    }
	    
	    @Test
	    public void testPostSuccess() throws IOException {
	    	
	    	itemServlet.doPost(requestMock, responseMock);
	    	
	    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	    }
	    
	    @Test
	    public void testPostFail() throws IOException {
	    	
	    	when(itemDaoMock.update(itemMock)).thenReturn(null);
	    	
	    	itemServlet.doPost(requestMock, responseMock);
	    	
	    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	    
	    @Test
	    public void testPost_deserializeFail() throws IOException, ItemDeserializationException{
	    	
	    	when(itemDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new ItemDeserializationException());
	    	
	    	itemServlet.doPost(requestMock, responseMock);
	    	
	    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }
}
