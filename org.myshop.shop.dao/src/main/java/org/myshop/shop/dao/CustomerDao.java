package org.myshop.shop.dao;

import java.util.List;

import org.myshop.shop.model.Customer;

public interface CustomerDao {

	public void create(Customer customer);
	
	public List<Customer> read();
	
	public Customer get(String id);
	
	public Customer update(Customer customer);
	
	public void delete(Customer customer);
}
