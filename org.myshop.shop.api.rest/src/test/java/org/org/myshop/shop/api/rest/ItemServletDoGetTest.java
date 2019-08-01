package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

public class ItemServletDoGetTest {
	
	private static final String TEST_JSON_STRING = "{}";

    private ItemServlet itemServlet;
    
    @Mock
    private ItemSerializer itemSerializerMock;
    
    @Mock
    private ItemDao itemDaoMock;
    
    @Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private PrintWriter printWriterMock;
    
    @Mock
    private List<Item> itemListMock;
    
    @Before
    public void setup() throws IOException{
    	MockitoAnnotations.initMocks(this);
    	
    	itemServlet = new ItemServlet();
    	itemServlet.setItemDao(itemDaoMock);
    	itemServlet.setItemSerializer(itemSerializerMock);
    	
    	when(itemDaoMock.read()).thenReturn(itemListMock);
    	when(itemSerializerMock.serializeList(itemListMock)).thenReturn(TEST_JSON_STRING);
    	when(responseMock.getWriter()).thenReturn(printWriterMock);
    }
    
    @Test
    public void testDoGet() throws IOException{
    	itemServlet.doGet(requestMock, responseMock);
    	
    	verify(itemDaoMock).read();
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testDoGetNullList() throws IOException{
    	when(itemDaoMock.read()).thenReturn(null);
    	itemServlet.doGet(requestMock, responseMock);

    	verify(itemDaoMock).read();
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	
    }
    
    @Test
    public void testDoGetEmptyList() throws IOException{
    	when(itemDaoMock.read()).thenReturn(Collections.<Item>emptyList());
    	itemServlet.doGet(requestMock, responseMock);

    	verify(itemDaoMock).read();
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
    
}
