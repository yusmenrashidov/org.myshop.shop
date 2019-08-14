package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.dao.PostedSalesOrderDao;
import org.myshop.shop.model.PostedSalesOrder;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PostedSalesOrderServletDoDeleteTest {

private static final String TEST_POSTED_SALES_ORDER_ID = "test_posted_sales_order_id";
	
	private PostedSalesOrderServlet postedSalesOrderServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private PostedSalesOrderDao postedSalesOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedSalesOrder postedSalesOrderMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		postedSalesOrderServlet = new PostedSalesOrderServlet();
		postedSalesOrderServlet.setPostedSalesOrderDao(postedSalesOrderDaoMock);
		postedSalesOrderServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_POSTED_SALES_ORDER_ID);
		when(postedSalesOrderDaoMock.get(TEST_POSTED_SALES_ORDER_ID)).thenReturn(postedSalesOrderMock);
	}
	
	@Test
	public void testSuccess() {
		
		postedSalesOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(postedSalesOrderDaoMock.get(TEST_POSTED_SALES_ORDER_ID)).thenReturn(null);
		
		postedSalesOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
}
