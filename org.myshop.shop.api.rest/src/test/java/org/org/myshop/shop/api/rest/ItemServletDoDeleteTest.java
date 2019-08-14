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
import org.myshop.shop.api.rest.servlet.util.implementation.UrlReader;
import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;

public class ItemServletDoDeleteTest {
   
	private static final String TEST_ITEM_ID = "test_item_id";

    private ItemServlet itemServlet;
    
    @Mock
    private UrlReader urlReaderMock;
    
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
    	itemServlet.setItemUlrReader(urlReaderMock);
    	
    	when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_ITEM_ID);
    	when(itemDaoMock.get(TEST_ITEM_ID)).thenReturn(itemMock);	
    }
    
    @Test
    public void deleteSuccess() throws IOException{
    	
    	itemServlet.doDelete(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void deleteFailed() throws IOException{
    	
    	when(itemDaoMock.get(TEST_ITEM_ID)).thenReturn(null);
    	
    	itemServlet.doDelete(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
}
