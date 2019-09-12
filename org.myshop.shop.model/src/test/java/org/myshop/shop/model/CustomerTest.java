package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.myshop.shop.model.Customer;

public class CustomerTest {

	@Test
	public void testFieldsAreNotNullOnNewCustomer() {
		Customer customer = new Customer();

		assertNotNull("Id should not be null on new Customer ", customer.getId());
		assertEquals("Id should not be empty on new Customer ", "", customer.getId());

		assertNotNull("Name should not be null on new Customer", customer.getName());
		assertEquals("Name should be empty on new Customer", "", customer.getName());
	}
}
