package org.org.myshop.shop.api.rest.servlet.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mockito.Mock;

import org.myshop.shop.api.rest.servlet.util.implementation.Serializer;
import org.myshop.shop.model.Customer;

import java.util.List;

public class SerializerTest {
	
	@Mock
	private List<Customer> customerList;
	
	@Test
	public void testSerialization_customer() {
		ISerializer<Customer> customerSerializer = new Serializer<Customer>(Customer.class);
		
		String serialized = customerSerializer.serializeList(customerList);
		
		assertNotNull(serialized);
	}
}
