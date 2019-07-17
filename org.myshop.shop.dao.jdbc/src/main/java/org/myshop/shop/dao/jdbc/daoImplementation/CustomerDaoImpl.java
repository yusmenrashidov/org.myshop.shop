package org.myshop.shop.dao.jdbc.daoImplementation;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

	public void create(Customer customer) {
		
		String querry = "INSERT INTO customers(customerId, name) VALUES(?, ?)";	
	}

	public List<Customer> read() {
		
		String querry ="SELECT * FROM customers";
		return null;
	}

	public Customer get(String id) {
		
		String querry = "SELECT * FROM customers WHERE customerId= ?";
		
		return null;
	}

	public Customer update(Customer customer) {
		
		String querry = "UPDATE customers SET customerId = ?, name= ? where customerId =?";
		
		return null;
	}

	public void delete(Customer customer) {
		
		String querry = "DELETE FROM customers WHERE customerId = ?";
	}

	
	
}
