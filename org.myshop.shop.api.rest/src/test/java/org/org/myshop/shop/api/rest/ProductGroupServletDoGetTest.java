package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;

import org.org.myshop.shop.api.rest.servlet.util.ISerializer;

public class ProductGroupServletDoGetTest {

	private ProductGroupServlet productGroupServlet;
	
	private final static String JSON_STRING = "{}";
	
	@Mock
	private ISerializer<ProductGroup> serializerMock;
	
	@Mock
	private ProductGroupDao productGroupDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private List<ProductGroup> listMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		productGroupServlet = new ProductGroupServlet();
		productGroupServlet.setSerializer(serializerMock);
		productGroupServlet.setProductGroupDao(productGroupDaoMock);
		
		when(productGroupDaoMock.read()).thenReturn(listMock);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		when(serializerMock.serializeList(listMock)).thenReturn(JSON_STRING);
	}
	
	@Test
	public void testSuccess() throws IOException {
		
		productGroupServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(productGroupDaoMock.read()).thenReturn(Collections.<ProductGroup>emptyList());
		
        productGroupServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testListFailed() throws IOException {
		when(productGroupDaoMock.read()).thenReturn(null);
		
		productGroupServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
}
