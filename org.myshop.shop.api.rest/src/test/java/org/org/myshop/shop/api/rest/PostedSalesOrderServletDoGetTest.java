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

import org.myshop.shop.dao.PostedSalesOrderDao;
import org.myshop.shop.model.PostedSalesOrder;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;

public class PostedSalesOrderServletDoGetTest {

private static final String TEST_JSON_STRING = "{}";
	
	private PostedSalesOrderServlet postedSalesOrderServlet;
	
	@Mock
	private ISerializer<PostedSalesOrder> serializerMock;
	
	@Mock
	private PostedSalesOrderDao postedSalesOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<PostedSalesOrder> postedSalesOrderList;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		postedSalesOrderServlet = new PostedSalesOrderServlet();
		postedSalesOrderServlet.setPostedSalesOrderDao(postedSalesOrderDaoMock);
		postedSalesOrderServlet.setSerializer(serializerMock);
		
		when(postedSalesOrderDaoMock.read()).thenReturn(postedSalesOrderList);
		when(serializerMock.serializeList(postedSalesOrderList)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		postedSalesOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(postedSalesOrderDaoMock.read()).thenReturn(Collections.<PostedSalesOrder>emptyList());
		
		postedSalesOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(postedSalesOrderDaoMock.read()).thenReturn(null);
		
		postedSalesOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
