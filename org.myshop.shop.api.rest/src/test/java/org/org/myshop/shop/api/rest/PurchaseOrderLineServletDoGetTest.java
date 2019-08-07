package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.PurchaseOrderLineDao;
import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderLineSerializer;

public class PurchaseOrderLineServletDoGetTest {

private static final String TEST_JSON_STRING = "{}";
	
	private PurchaseOrderLineServlet purchaseOrderLineServlet;
	
	@Mock
	private IPurchaseOrderLineSerializer purchaseOrderLineSerializerMock;
	
	@Mock
	private PurchaseOrderLineDao purchaseOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<PurchaseOrderLine> purchaseOrderLineListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		purchaseOrderLineServlet = new PurchaseOrderLineServlet();
		purchaseOrderLineServlet.setPurchaseOrderLineDao(purchaseOrderLineDaoMock);
		purchaseOrderLineServlet.setPurchaseOrderLineSerializer(purchaseOrderLineSerializerMock);
		
		when(purchaseOrderLineDaoMock.read()).thenReturn(purchaseOrderLineListMock);
		when(purchaseOrderLineSerializerMock.serializerList(purchaseOrderLineListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		purchaseOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(purchaseOrderLineDaoMock.read()).thenReturn(Collections.<PurchaseOrderLine>emptyList());
		
		purchaseOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(purchaseOrderLineDaoMock.read()).thenReturn(null);
		
		purchaseOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
