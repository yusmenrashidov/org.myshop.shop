package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.util.IVendorSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class VendorServletDoGetTest {

	private static final String TEST_JSON_STRING = "{}";
	
	private VendorServlet vendorServlet;
	
	@Mock
	private IVendorSerializer vendorSerializerMock;
	
	@Mock
	private VendorDao vendorDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<Vendor> vendorListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		vendorServlet = new VendorServlet();
		vendorServlet.setVendorDao(vendorDaoMock);
		vendorServlet.setVendorSerializer(vendorSerializerMock);
		
		when(vendorDaoMock.read()).thenReturn(vendorListMock);
		when(vendorSerializerMock.serializeList(vendorListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
	}
	
	@Test
	public void testSuccess() throws IOException {
		vendorServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException{
		when(vendorDaoMock.read()).thenReturn(Collections.<Vendor>emptyList());
		
		vendorServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException {
		when(vendorDaoMock.read()).thenReturn(null);
		
		vendorServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
