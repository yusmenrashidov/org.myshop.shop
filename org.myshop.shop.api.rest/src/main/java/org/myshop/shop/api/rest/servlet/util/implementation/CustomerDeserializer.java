package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.exc.CustomerDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.ICustomerDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class CustomerDeserializer implements ICustomerDeserializer {

	public Customer deserialize(String jsonString) throws CustomerDeserializationException{
		
		Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new CustomerDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new CustomerDeserializationException("Input string is empty");
			}
			
			Customer customer = gson.fromJson(jsonString, Customer.class);
			
			return customer;
		}catch(JsonSyntaxException e) {
			throw new CustomerDeserializationException(e);
		}
		
	}

}
