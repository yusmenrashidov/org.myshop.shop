package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PostedSalesOrderLineDao;
import org.myshop.shop.model.PostedSalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PostedSalesOrderLineServletDoDeleteTest {

private static final String TEST_POSTED_SALES_ORDER_LINE_ID = "test_posted_sales_order_line_id";
	
	private PostedSalesOrderLineServlet postedSalesOrderLineServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private PostedSalesOrderLineDao postedSalesOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedSalesOrderLine postedSalesOrderLineMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		postedSalesOrderLineServlet = new PostedSalesOrderLineServlet();
		postedSalesOrderLineServlet.setPostedSalesOrderLineDao(postedSalesOrderLineDaoMock);
		postedSalesOrderLineServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_POSTED_SALES_ORDER_LINE_ID);
		when(postedSalesOrderLineDaoMock.get(TEST_POSTED_SALES_ORDER_LINE_ID)).thenReturn(postedSalesOrderLineMock);
	}
	
	@Test
	public void testSuccess() {
		
		postedSalesOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(postedSalesOrderLineDaoMock.get(TEST_POSTED_SALES_ORDER_LINE_ID)).thenReturn(null);
		
		postedSalesOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
