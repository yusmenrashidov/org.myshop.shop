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

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.util.IPostedPurchaseOrderSerializer;

public class PostedPurchaseOrderServletDoGetTest {

private static final String TEST_JSON_STRING = "{}";
	
	private PostedPurchaseOrderServlet postedPurchaseOrderServlet;
	
	@Mock
	private IPostedPurchaseOrderSerializer postedPurchaseOrderSerializerMock;
	
	@Mock
	private PostedPurchaseOrderDao postedPurchaseOrderDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<PostedPurchaseOrder> postedPurchaseOrderListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		postedPurchaseOrderServlet = new PostedPurchaseOrderServlet();
		postedPurchaseOrderServlet.setPostedPurchaseOrderDao(postedPurchaseOrderDaoMock);
		postedPurchaseOrderServlet.setPostedPurchaseOrderSerializer(postedPurchaseOrderSerializerMock);
		
		when(postedPurchaseOrderDaoMock.read()).thenReturn(postedPurchaseOrderListMock);
		when(postedPurchaseOrderSerializerMock.serializerList(postedPurchaseOrderListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		postedPurchaseOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(postedPurchaseOrderDaoMock.read()).thenReturn(Collections.<PostedPurchaseOrder>emptyList());
		
		postedPurchaseOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(postedPurchaseOrderDaoMock.read()).thenReturn(null);
		
		postedPurchaseOrderServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
