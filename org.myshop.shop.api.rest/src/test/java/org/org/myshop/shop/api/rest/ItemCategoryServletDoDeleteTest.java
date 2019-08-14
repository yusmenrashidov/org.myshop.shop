package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class ItemCategoryServletDoDeleteTest {

	private static final String TEST_ITEM_CATEGORY_ID = "test_item_category_id";
	
	private ItemCategoryServlet itemCategoryServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private ItemCategoryDao itemCategoryDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private ItemCategory itemCategoryMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		itemCategoryServlet = new ItemCategoryServlet();
		itemCategoryServlet.setItemCategoryDao(itemCategoryDaoMock);
		itemCategoryServlet.setUlrReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(itemCategoryDaoMock.get(TEST_ITEM_CATEGORY_ID)).thenReturn(itemCategoryMock);
	}
	
	@Test
	public void testSuccess() {
		
		itemCategoryServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void deleteFailed() {
		
		when(itemCategoryDaoMock.get(TEST_ITEM_CATEGORY_ID)).thenReturn(null);
		
		itemCategoryServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
}
