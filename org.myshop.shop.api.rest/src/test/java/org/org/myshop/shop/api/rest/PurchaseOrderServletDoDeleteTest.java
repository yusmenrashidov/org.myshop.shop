package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PurchaseOrderServletDoDeleteTest {
	
	private static final String TEST_PURCHASE_ORDER_ID = "test_purchase_order_id";
	
	private PurchaseOrderServlet purchaseOrderServlet;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PurchaseOrderDao purchaseOrderDaoMock;
	
	@Mock
	private PurchaseOrder purchaseOrderMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		purchaseOrderServlet = new PurchaseOrderServlet();
		purchaseOrderServlet.setPurchaseOrderDao(purchaseOrderDaoMock);
		purchaseOrderServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_PURCHASE_ORDER_ID);
		when(purchaseOrderDaoMock.get(TEST_PURCHASE_ORDER_ID)).thenReturn(purchaseOrderMock);
	}
	
	@Test
	public void testSuccess() {
		purchaseOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(purchaseOrderDaoMock.get(TEST_PURCHASE_ORDER_ID)).thenReturn(null);
		
		purchaseOrderServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
