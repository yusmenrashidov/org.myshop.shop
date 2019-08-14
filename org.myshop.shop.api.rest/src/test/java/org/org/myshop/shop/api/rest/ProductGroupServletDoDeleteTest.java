package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class ProductGroupServletDoDeleteTest {

	private final static String TEST_PRODUCT_GROUP_ID = "test_product_group_id";
	
	private ProductGroupServlet productGroupServlet;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private ProductGroupDao productGroupDaoMock;
	
	@Mock
	private ProductGroup productGroupMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		productGroupServlet = new ProductGroupServlet();
		productGroupServlet.setUrlReader(urlReaderMock);
		productGroupServlet.setProductGroupDao(productGroupDaoMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_PRODUCT_GROUP_ID);
		when(productGroupDaoMock.get(TEST_PRODUCT_GROUP_ID)).thenReturn(productGroupMock);
	}
	
	@Test
	public void testSuccess() {
		
		productGroupServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testQueryFailed() {
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(null);
		
        productGroupServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testNullProductGroup() {
		when(productGroupDaoMock.get(TEST_PRODUCT_GROUP_ID)).thenReturn(null);
		
		productGroupServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
