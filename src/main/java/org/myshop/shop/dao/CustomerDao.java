package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Customer;
import org.myshop.shop.model.Item;

public interface CustomerDao {

	public void addCustomer(Customer customer);
	public List<Customer> getCustomers();
	public Customer getCustomer(String id);
	public List<Customer> getCustomersByItem(Item item);
	public void editCustomer(Customer customer);
}
