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

import org.myshop.shop.dao.PostedPurchaseOrderLineDao;
import org.myshop.shop.model.PostedPurchaseOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;

public class PostedPurchaseOrderLineServletDoGetTest {

private static final String TEST_JSON_STRING = "{}";
	
	private PostedPurchaseOrderLineServlet postedPurchaseOrderLineServlet;
	
	@Mock
	private ISerializer<PostedPurchaseOrderLine> serializerMock;
	
	@Mock
	private PostedPurchaseOrderLineDao postedPurchaseOrderLineDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<PostedPurchaseOrderLine> postedPurchaseOrderLineListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		postedPurchaseOrderLineServlet = new PostedPurchaseOrderLineServlet();
		postedPurchaseOrderLineServlet.setPostedPurchaseOrderLineDao(postedPurchaseOrderLineDaoMock);
		postedPurchaseOrderLineServlet.setSerializer(serializerMock);
		
		when(postedPurchaseOrderLineDaoMock.read()).thenReturn(postedPurchaseOrderLineListMock);
		when(serializerMock.serializeList(postedPurchaseOrderLineListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		postedPurchaseOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(postedPurchaseOrderLineDaoMock.read()).thenReturn(Collections.<PostedPurchaseOrderLine>emptyList());
		
		postedPurchaseOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(postedPurchaseOrderLineDaoMock.read()).thenReturn(null);
		
		postedPurchaseOrderLineServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
