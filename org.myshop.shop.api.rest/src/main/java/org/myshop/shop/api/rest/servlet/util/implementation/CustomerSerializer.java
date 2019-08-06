package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.Customer;
import org.org.myshop.shop.api.rest.servlet.util.ICustomerSerializer;

import com.google.gson.Gson;

public class CustomerSerializer implements ICustomerSerializer{

	public String serializerList(List<Customer> customerList) {
		
		Gson gson = new Gson();
				
		String listToJson = gson.toJson(customerList);		
		
		return listToJson;
	}

}
