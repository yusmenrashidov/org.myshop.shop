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
import org.org.myshop.shop.api.rest.servlet.exc.ItemCategoryDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategoryDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemCategoryServletDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private ItemCategoryServlet itemCategoryServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IItemCategoryDeserializer itemCategoryDeserializerMock;
	
	@Mock
	private ItemCategoryDao itemCategoryDaoMock;

    @Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private ItemCategory itemCategoryMock;
	
    @Before
    public void setup() throws IOException, ItemCategoryDeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	itemCategoryServlet = new ItemCategoryServlet();
    	
    	itemCategoryServlet.setRequestBodyReader(requestBodyReaderMock);
    	itemCategoryServlet.setItemCategoryDeserializer(itemCategoryDeserializerMock);
    	itemCategoryServlet.setItemCategoryDao(itemCategoryDaoMock);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(itemCategoryDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(itemCategoryMock);
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
    public void testDeserializeFailed() throws ItemCategoryDeserializationException, IOException {
    	
    	when(itemCategoryDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new ItemCategoryDeserializationException());
    	
    	itemCategoryServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
