package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class SalesOrderLineServletDoDeleteTest {

private static final String TEST_SALES_ORDER_LINE_ID = "test_sales_order_line_id";
	
	private SalesOrderLineServlet salesOrderLineServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private SalesOrderLineDao salesOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private SalesOrderLine salesOrderLineMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		salesOrderLineServlet = new SalesOrderLineServlet();
		salesOrderLineServlet.setSalesOrderLineDao(salesOrderLineDaoMock);
		salesOrderLineServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_SALES_ORDER_LINE_ID);
		when(salesOrderLineDaoMock.get(TEST_SALES_ORDER_LINE_ID)).thenReturn(salesOrderLineMock);
	}
	
	@Test
	public void testSuccess() {
		
		salesOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(salesOrderLineDaoMock.get(TEST_SALES_ORDER_LINE_ID)).thenReturn(null);
		
		salesOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
