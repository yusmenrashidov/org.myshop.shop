package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class VendorServletDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private VendorServlet vendorServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<Vendor> deserializer;
	
	@Mock
    private HttpServletRequest requestMock;
    
    @Mock
    private HttpServletResponse responseMock;
    
    @Mock
    private VendorDao vendorDaoMock;
    
    @Mock
    private Vendor vendorMock;
    
    @Before
    public void setup() throws IOException, DeserializationException {
    	MockitoAnnotations.initMocks(this);
    	
    	vendorServlet = new VendorServlet();
    	vendorServlet.setRequestBodyReader(requestBodyReaderMock);
    	vendorServlet.setVendorDao(vendorDaoMock);
    	vendorServlet.setDeserializer(deserializer);
    	
    	when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
    	when(deserializer.deserialize(TEST_REQUEST_BODY)).thenReturn(vendorMock);
    	when(vendorDaoMock.update(vendorMock)).thenReturn(vendorMock);
    }
    
    @Test
    public void testSuccess() throws IOException {
    	vendorServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_OK);
    }
    
    @Test
    public void testFailed() throws IOException {
    	when(vendorDaoMock.update(vendorMock)).thenReturn(null);
    	
    	vendorServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testDeserializationFailed() throws DeserializationException, IOException {
    	when(deserializer.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
    	
    	vendorServlet.doPost(requestMock, responseMock);
    	
    	verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
	
}
