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

public class ProductGroupServletDoPostTest {

	private static final String TEST_REQUEST_BODY = "test_request_body";
	
	private ProductGroupServlet productGroupServlet;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private IRequestBodyReader requestBodyReaderMock;
	
	@Mock
	private IDeserializer<ProductGroup> deserializerMock;
	
	@Mock
	private ProductGroupDao productGroupDaoMock;
	
	@Mock
	private ProductGroup productGroupMock;
	
	@Before
	public void setup() throws IOException, DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		productGroupServlet = new ProductGroupServlet();
			
		productGroupServlet.setRequestBodyReader(requestBodyReaderMock);
		productGroupServlet.setDeserializer(deserializerMock);
		productGroupServlet.setProductGroupDao(productGroupDaoMock);
		
		when(requestBodyReaderMock.readBody(requestMock)).thenReturn(TEST_REQUEST_BODY);
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenReturn(productGroupMock);
		when(productGroupDaoMock.update(productGroupMock)).thenReturn(productGroupMock);
	
	}
	
	@Test
	public void testSuccess() throws IOException {
		
		productGroupServlet.doPost(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testFailed() throws IOException {
		when(productGroupDaoMock.update(productGroupMock)).thenReturn(null);
		
		productGroupServlet.doPost(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testDeserializationFailed() throws IOException, DeserializationException {
		when(deserializerMock.deserialize(TEST_REQUEST_BODY)).thenThrow(new DeserializationException());
		
		productGroupServlet.doPost(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
}
