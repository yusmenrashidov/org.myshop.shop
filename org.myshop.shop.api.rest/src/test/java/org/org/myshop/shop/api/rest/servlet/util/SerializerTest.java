package org.org.myshop.shop.api.rest.servlet.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.Customer;

public class SerializerTest {
    
    @Test
    public void test() {
        ISerializer<MyClass> itemSerializer = new Serializer<MyClass>(MyClass.class);
        
        MyClass serializedItem = itemSerializer.serialize("{\n" + 
                "    \"s\" : \"alabala\",\n" + 
                "    \"i\" : 123\n" + 
                "}");
        
        assertNotNull(serializedItem);
    }

    @Test
    public void test_customer() {
        ISerializer<Customer> customerSerializer = new Serializer<Customer>(Customer.class);
        
        Customer customer = customerSerializer.serialize("{\n" + 
                "    \"id\" : \"alabala\",\n" + 
                "    \"name\" : \"portokola\"\n" + 
                "}");
        
        assertEquals("alabala", customer.getId());
        assertEquals("portokola", customer.getName());
    }
}
