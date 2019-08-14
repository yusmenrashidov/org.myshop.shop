package org.org.myshop.shop.api.rest.servlet.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import org.myshop.shop.api.rest.servlet.util.implementation.Deserializer;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;

public class DeserializerTest {
    
	private static final String TEST_JSON_STRING = "{}";
    
	@Mock
	private IDeserializer deserializerMock;
	
	@Mock
	private Customer customerMock;
	
	@Before
	public void setup() throws DeserializationException {
		MockitoAnnotations.initMocks(this);
		
		when(deserializerMock.deserialize(TEST_JSON_STRING)).thenReturn(customerMock);
	}
	
	
    @Test
    public void test_customer() throws DeserializationException {
        IDeserializer<Customer> customerSerializer = new Deserializer<Customer>(Customer.class);
        
        Customer customer = customerSerializer.deserialize("{\n" + 
                "    \"id\" : \"alabala\",\n" + 
                "    \"name\" : \"portokola\"\n" + 
                "}");
        
        assertEquals("alabala", customer.getId());
        assertEquals("portokola", customer.getName());
    }
    
    
}
