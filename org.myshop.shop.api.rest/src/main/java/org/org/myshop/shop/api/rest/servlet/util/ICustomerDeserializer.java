package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.exc.CustomerDeserializationException;

public interface ICustomerDeserializer {

	public Customer deserialize(String jsonString) throws CustomerDeserializationException;
	
}
