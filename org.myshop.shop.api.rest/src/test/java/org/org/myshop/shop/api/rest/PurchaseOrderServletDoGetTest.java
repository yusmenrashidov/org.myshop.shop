package org.org.myshop.shop.api.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderSerializer;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class PurchaseOrderServletDoGetTest {

	private static final String TEST_JSON_STRING = "{}";
	
	private PurchaseOrderServlet purchaseOrderServlet;
	
	@Mock
	private IPurchaseOrderSerializer purchaseOrderSerializerMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PurchaseOrderDao purchaseOrderDaoMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<PurchaseOrder> purchaseOrderListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		purchaseOrderServlet = new PurchaseOrderServlet();
		purchaseOrderServlet.setPurchaseOrderDao(purchaseOrderDaoMock);
		purchaseOrderServlet.setPurchaseOrderSerializer(purchaseOrderSerializerMock);
		
		when(purchaseOrderDaoMock.read()).thenReturn(purchaseOrderListMock);
		when(purchaseOrderSerializerMock.serializerList(purchaseOrderListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
	}
	
	@Test
	public void testSuccess() throws IOException {
		purchaseOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(purchaseOrderDaoMock.read()).thenReturn(Collections.<PurchaseOrder>emptyList());
		
		purchaseOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(purchaseOrderDaoMock.read()).thenReturn(null);
		
		purchaseOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
