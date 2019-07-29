package org.org.myshop.shop.api.rest;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.jdbc.JdbcItemDao;
import org.myshop.shop.model.Item;

import com.google.gson.Gson;

public class ItemServletTest {

	@Mock
	private Connection sqlConnectioMock;
	
	@Mock 
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private Item itemMock;
	
	@Mock
	private JdbcItemDao itemDaoMock;
	
	@Mock
	private BufferedReader readerMock;
	
	@Mock
	private ItemServlet itemServletMock;
	

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		Gson gson = new Gson();
		
		when(requestMock.getReader()).thenReturn(readerMock);
		when(gson.fromJson(readerMock, Item.class)).thenReturn(itemMock);
		when(itemServletMock.requestToObject(requestMock)).thenReturn(itemMock);
		
	}
	
	@Test
	public void testItemServlet() {
		
		assertNotNull(requestMock);
		assertNotNull(responseMock);
		
	}

	@Test
	public void testRequestToObject() throws IOException {
		Item item = itemServletMock.requestToObject(requestMock);
		
		verify(requestMock).getReader()
		verify(itemDaoMock).create(itemMock);
		assertNotNull(item);
		
	}
	
}
