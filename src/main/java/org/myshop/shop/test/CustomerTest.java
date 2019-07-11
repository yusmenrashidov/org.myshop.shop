package org.myshop.shop;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.Customer;

public class CustomerTest {

    @Test
    public void testFieldsAreNotNullOnNewCustomer() {
        Customer customer = new Customer();
        
        assertNotNull(customer.getId());
        assertNotNull(customer.getName());
    }
}
