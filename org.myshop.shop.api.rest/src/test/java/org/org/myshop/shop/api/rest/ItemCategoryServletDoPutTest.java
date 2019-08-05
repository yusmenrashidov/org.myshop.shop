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

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;

import org.org.myshop.shop.api.rest.servlet.exc.ItemCategoryDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategoryDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemCategoryServletDoPutTest {

	private static final String TEST_REQEUST_BODY = "test_request_body";
	
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
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQEUST_BODY);
		
		when(itemCategoryDeserializerMock.deserialize(TEST_REQEUST_BODY)).thenReturn(itemCategoryMock);
		
		itemCategoryServlet = new ItemCategoryServlet();
		itemCategoryServlet.setRequestBodyReader(requestBodyReaderMock);
		itemCategoryServlet.setItemCategoryDeserializer(itemCategoryDeserializerMock);
		itemCategoryServlet.setItemCategoryDao(itemCategoryDaoMock);
		
	}
	
	@Test
	public void testSuccess() {
		itemCategoryServlet.doPut(requestMock, responseMock);
		
		  verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReadRequestBodyFailed() throws IOException {
		 when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		 
		 itemCategoryServlet.doPut(requestMock, responseMock);
		 
		 verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws ItemCategoryDeserializationException {
		when(itemCategoryDeserializerMock.deserialize(TEST_REQEUST_BODY)).thenThrow(new ItemCategoryDeserializationException());
		
		itemCategoryServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
	}
}
