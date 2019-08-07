package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class SalesOrderServletDoDeleteTest {

	private static final String TEST_SALES_ORDER_ID = "test_sales_order_id";
	
	private SalesOrderServlet salesOrderServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private SalesOrderDao salesOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private SalesOrder salesOrderMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		salesOrderServlet = new SalesOrderServlet();
		salesOrderServlet.setSalesOrderDao(salesOrderDaoMock);
		salesOrderServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_SALES_ORDER_ID);
		when(salesOrderDaoMock.get(TEST_SALES_ORDER_ID)).thenReturn(salesOrderMock);
	}
	
	@Test
	public void testSuccess() {
		
		salesOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(salesOrderDaoMock.get(TEST_SALES_ORDER_ID)).thenReturn(null);
		
		salesOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
