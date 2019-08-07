package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderSerializer;

public class SalesOrderServletDoGetTest {

	private static final String TEST_JSON_STRING = "{}";
	
	private SalesOrderServlet salesOrderServlet;
	
	@Mock
	private ISalesOrderSerializer salesOrderSerializerMock;
	
	@Mock
	private SalesOrderDao salesOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<SalesOrder> salesOrderListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		salesOrderServlet = new SalesOrderServlet();
		salesOrderServlet.setSalesOrderDao(salesOrderDaoMock);
		salesOrderServlet.setSalesOrderSerializer(salesOrderSerializerMock);
		
		when(salesOrderDaoMock.read()).thenReturn(salesOrderListMock);
		when(salesOrderSerializerMock.serializerList(salesOrderListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
	}
	
	@Test
	public void testSuccess() throws IOException {
		salesOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(salesOrderDaoMock.read()).thenReturn(Collections.<SalesOrder>emptyList());
		
		salesOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(salesOrderDaoMock.read()).thenReturn(null);
		
		salesOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
}
