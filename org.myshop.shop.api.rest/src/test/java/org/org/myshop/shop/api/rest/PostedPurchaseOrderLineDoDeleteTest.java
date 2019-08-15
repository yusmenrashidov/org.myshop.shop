package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PostedPurchaseOrderLineDao;
import org.myshop.shop.model.PostedPurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PostedPurchaseOrderLineDoDeleteTest {

private static final String TEST_POSTED_PURCHASE_ORDER_LINE_ID = "test_posted_purchase_order_line_id";
	
	private PostedPurchaseOrderLineServlet postedPurchaseOrderLineServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private PostedPurchaseOrderLineDao postedPurchaseOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedPurchaseOrderLine postedPurchaseOrderLineMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		postedPurchaseOrderLineServlet = new PostedPurchaseOrderLineServlet();
		postedPurchaseOrderLineServlet.setPostedPurchaseOrderLineDao(postedPurchaseOrderLineDaoMock);
		postedPurchaseOrderLineServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_POSTED_PURCHASE_ORDER_LINE_ID);
		when(postedPurchaseOrderLineDaoMock.get(TEST_POSTED_PURCHASE_ORDER_LINE_ID)).thenReturn(postedPurchaseOrderLineMock);
	}
	
	@Test
	public void testSuccess() {
		
		postedPurchaseOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(postedPurchaseOrderLineDaoMock.get(TEST_POSTED_PURCHASE_ORDER_LINE_ID)).thenReturn(null);
		
		postedPurchaseOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
