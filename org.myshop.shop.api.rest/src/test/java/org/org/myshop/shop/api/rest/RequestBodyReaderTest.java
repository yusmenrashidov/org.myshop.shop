package org.org.myshop.shop.api.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.api.rest.servlet.utilImpl.RequestBodyReader;

public class RequestBodyReaderTest {

    private static final String TEST_REQUEST_BODY_LINE = "test_request_body_line";

    @Mock
    private HttpServletRequest unableToGetReaderRequest;
    
    @Mock
    private HttpServletRequest problemReadingRequest;

    @Mock
    private HttpServletRequest properRequest;
    
    @Mock
    private BufferedReader problematicReaderMock;
    
    @Mock
    private BufferedReader properReaderMock;

    private RequestBodyReader requestBodyReader;
    
    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        
        when(unableToGetReaderRequest.getReader()).thenThrow(new IOException());
        when(problemReadingRequest.getReader()).thenReturn(problematicReaderMock);
        when(properRequest.getReader()).thenReturn(properReaderMock);
        
        when(problematicReaderMock.readLine()).thenReturn(TEST_REQUEST_BODY_LINE).thenThrow(new IOException());
        when(properReaderMock.readLine()).thenReturn(TEST_REQUEST_BODY_LINE).thenReturn(null);
        
        requestBodyReader = new RequestBodyReader();
    }
    
    @Test(expected = IOException.class)
    public void test_exceptionGettingReader() throws IOException {
        requestBodyReader.readBody(unableToGetReaderRequest);
    }
    
    @Test(expected = IOException.class)
    public void test_exceptionReadingRequest() throws IOException {
        requestBodyReader.readBody(problemReadingRequest);
    }

    @Test
    public void test() throws IOException {
        String requestBody = requestBodyReader.readBody(properRequest);
        
        assertEquals(TEST_REQUEST_BODY_LINE, requestBody);
    }
}
