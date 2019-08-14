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

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemCategoryServletDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private ItemCategoryServlet itemCategoryServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<ItemCategory> deserializerMock;
	
	@Mock
	private ItemCategoryDao itemCategoryDaoMock;

    @Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private ItemCategory itemCategoryMock;
	
    @Before
    public void setup() throws IOException, DeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	itemCategoryServlet = new ItemCategoryServlet();
    	
    	itemCategoryServlet.setRequestBodyReader(requestBodyReaderMock);
    	itemCategoryServlet.setDeserializer(deserializerMock);
    	itemCategoryServlet.setItemCategoryDao(itemCategoryDaoMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(itemCategoryMock);
    	when(itemCategoryDaoMock.update(itemCategoryMock)).thenReturn(itemCategoryMock);
    
    }
    
    @Test
    public void testSuccess() throws IOException {
    	
    	itemCategoryServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	
    	when(itemCategoryDaoMock.update(itemCategoryMock)).thenReturn(null);
    	
    	itemCategoryServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testDeserializeFailed() throws IOException, DeserializationException {
    	
    	when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
    	
    	itemCategoryServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
