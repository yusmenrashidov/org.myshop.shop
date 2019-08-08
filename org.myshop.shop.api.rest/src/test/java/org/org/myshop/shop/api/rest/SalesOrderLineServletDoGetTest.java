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

import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderLineSerializer;

public class SalesOrderLineServletDoGetTest {

private static final String TEST_JSON_STRING = "{}";
	
	private SalesOrderLineServlet salesOrderLineServlet;
	
	@Mock
	private ISalesOrderLineSerializer salesOrderLineSerializerMock;
	
	@Mock
	private SalesOrderLineDao salesOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<SalesOrderLine> salesOrderLineListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		salesOrderLineServlet = new SalesOrderLineServlet();
		salesOrderLineServlet.setSalesOrderLineDao(salesOrderLineDaoMock);
		salesOrderLineServlet.setSalesOrderLineSerializer(salesOrderLineSerializerMock);
		
		when(salesOrderLineDaoMock.read()).thenReturn(salesOrderLineListMock);
		when(salesOrderLineSerializerMock.serializerList(salesOrderLineListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		salesOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(salesOrderLineDaoMock.read()).thenReturn(Collections.<SalesOrderLine>emptyList());
		
		salesOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(salesOrderLineDaoMock.read()).thenReturn(null);
		
		salesOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
