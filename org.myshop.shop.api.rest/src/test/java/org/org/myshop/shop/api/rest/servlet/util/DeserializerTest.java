package org.org.myshop.shop.api.rest.servlet.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.myshop.shop.api.rest.servlet.util.implementation.Deserializer;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;

public class DeserializerTest {
    
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
