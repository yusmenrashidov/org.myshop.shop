package org.org.myshop.shop.api.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.exc.VendorDeserializationException;

import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.IVendorDeserializer;

public class VendorServletDoPutTest {

	private final String TEST_REQUEST_BODY = "test_request_body";
	
	private VendorServlet vendorServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IVendorDeserializer vendorDeserializerMock;
	
	@Mock
	private VendorDao vendorDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private Vendor vendorMock;
	
	@Before
	public void setup() throws IOException, VendorDeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		
		when(vendorDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(vendorMock);
		
		vendorServlet = new VendorServlet();
		vendorServlet.setRequestBodyReader(requestBodyReaderMock);
		vendorServlet.setVendorDeserializer(vendorDeserializerMock);
		vendorServlet.setVendorDao(vendorDaoMock);
	}
	
	@Test
	public void testSuccess() {
		
		vendorServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testReaderRequestBodyFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
		vendorServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testJSONStringFailed() throws VendorDeserializationException {
		when(vendorDeserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new VendorDeserializationException());
		
		vendorServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
