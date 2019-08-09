package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;

import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PostedPurchaseOrderServletDoDeleteTest {

private static final String TEST_POSTED_PURCHASE_ORDER_ID = "test_posted_purchase_order_id";
	
	private PostedPurchaseOrderServlet postedPurchaseOrderServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private PostedPurchaseOrderDao postedPurchaseOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PostedPurchaseOrder postedPurchaseOrderMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		postedPurchaseOrderServlet = new PostedPurchaseOrderServlet();
		postedPurchaseOrderServlet.setPostedPurchaseOrderDao(postedPurchaseOrderDaoMock);
		postedPurchaseOrderServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_POSTED_PURCHASE_ORDER_ID);
		when(postedPurchaseOrderDaoMock.get(TEST_POSTED_PURCHASE_ORDER_ID)).thenReturn(postedPurchaseOrderMock);
	}
	
	@Test
	public void testSuccess() {
		
		postedPurchaseOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(postedPurchaseOrderDaoMock.get(TEST_POSTED_PURCHASE_ORDER_ID)).thenReturn(null);
		
		postedPurchaseOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
