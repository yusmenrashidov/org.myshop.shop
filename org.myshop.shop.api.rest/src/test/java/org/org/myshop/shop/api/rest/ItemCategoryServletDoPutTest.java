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
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ItemCategoryServletDoPutTest {

	private static final String TEST_REQEUST_BODY = "test_request_body";
	
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
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQEUST_BODY);
		
		when(deserializerMock.deserialize(TEST_REQEUST_BODY)).thenReturn(itemCategoryMock);
		
		itemCategoryServlet = new ItemCategoryServlet();
		itemCategoryServlet.setRequestBodyReader(requestBodyReaderMock);
		itemCategoryServlet.setDeserializer(deserializerMock);
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
	public void testJSONStringFailed() throws DeserializationException {
		when(deserializerMock.deserialize(TEST_REQEUST_BODY)).thenThrow(new DeserializationException());
		
		itemCategoryServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
	}
}
