package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class VendorServletDoDeleteTest {

	private final static String TEST_VENDOR_ID = "test_vendor_id";
	
	private VendorServlet vendorServlet;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private IUrlReader urlReaderMock;
	
	@Mock
	private VendorDao vendorDaoMock;
	
	@Mock
	private Vendor vendorMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		vendorServlet = new VendorServlet();
		vendorServlet.setVendorDao(vendorDaoMock);
		vendorServlet.setUrlReader(urlReaderMock);
		
		when(urlReaderMock.getIdFromQuery(requestMock)).thenReturn(TEST_VENDOR_ID);
		when(vendorDaoMock.get(TEST_VENDOR_ID)).thenReturn(vendorMock);
	}
	
	@Test
	public void testSuccess() {
		vendorServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() {
		when(vendorDaoMock.get(TEST_VENDOR_ID)).thenReturn(null);
		
		vendorServlet.doDelete(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
