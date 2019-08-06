package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.Customer;

public interface ICustomerSerializer {

	public String serializerList(List<Customer> customerList);
	
}
