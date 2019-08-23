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
import org.myshop.shop.dao.PostedSalesOrderLineDao;
import org.myshop.shop.model.PostedSalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;

public class PostedSalesOrderLineServletDoGetTest {

private static final String TEST_JSON_STRING = "{}";
	
	private PostedSalesOrderLineServlet postedSalesOrderLineServlet;
	
	@Mock
	private ISerializer<PostedSalesOrderLine> serializerMock;
	
	@Mock
	private PostedSalesOrderLineDao postedSalesOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<PostedSalesOrderLine> postedSalesOrderLineListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		postedSalesOrderLineServlet = new PostedSalesOrderLineServlet();
		postedSalesOrderLineServlet.setPostedSalesOrderLineDao(postedSalesOrderLineDaoMock);
		postedSalesOrderLineServlet.setSerializer(serializerMock);
		
		when(postedSalesOrderLineDaoMock.read()).thenReturn(postedSalesOrderLineListMock);
		when(serializerMock.serializeList(postedSalesOrderLineListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		postedSalesOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(postedSalesOrderLineDaoMock.read()).thenReturn(Collections.<PostedSalesOrderLine>emptyList());
		
		postedSalesOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(postedSalesOrderLineDaoMock.read()).thenReturn(null);
		
		postedSalesOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
