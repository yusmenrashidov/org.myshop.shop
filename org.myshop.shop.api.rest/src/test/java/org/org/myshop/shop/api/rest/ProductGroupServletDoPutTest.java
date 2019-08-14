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

import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class ProductGroupServletDoPutTest {

	private final static String TEST_REQUEST_BODY = "test_request_body";
	
	private ProductGroupServlet productGroupServlet;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<ProductGroup> deserializerMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private ProductGroup productGroupMock;
	
	@Mock
	private ProductGroupDao productGroupDao;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		productGroupServlet = new ProductGroupServlet();
		
		productGroupServlet.setDeserializer(deserializerMock);
		productGroupServlet.setRequestBodyReader(requestBodyReaderMock);
		productGroupServlet.setProductGroupDao(productGroupDao);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(productGroupMock);
		
	}
	
	@Test
	public void testSuccess() throws IOException {
		
		productGroupServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Test
	public void testRequestBodyReaderFailed() throws IOException {
		when(requestBodyReaderMock.readBody(requestMock)).thenThrow(new IOException());
		
        productGroupServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	
	}
	
	@Test
	public void testDeserializationFailed() throws IOException, DeserializationException {
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		productGroupServlet.doPut(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
	}
}
