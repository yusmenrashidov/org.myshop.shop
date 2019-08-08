package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PurchaseOrderLineDao;
import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PurchaseOrderLineServletDoDeleteTest {

private static final String TEST_PURCHASE_ORDER_LINE_ID = "test_purchase_order_line_id";
	
	private PurchaseOrderLineServlet purchaseOrderLineServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private PurchaseOrderLineDao purchaseOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PurchaseOrderLine purchaseOrderLineMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		purchaseOrderLineServlet = new PurchaseOrderLineServlet();
		purchaseOrderLineServlet.setPurchaseOrderLineDao(purchaseOrderLineDaoMock);
		purchaseOrderLineServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_PURCHASE_ORDER_LINE_ID);
		when(purchaseOrderLineDaoMock.get(TEST_PURCHASE_ORDER_LINE_ID)).thenReturn(purchaseOrderLineMock);
	}
	
	@Test
	public void testSuccess() {
		
		purchaseOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(purchaseOrderLineDaoMock.get(TEST_PURCHASE_ORDER_LINE_ID)).thenReturn(null);
		
		purchaseOrderLineServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
