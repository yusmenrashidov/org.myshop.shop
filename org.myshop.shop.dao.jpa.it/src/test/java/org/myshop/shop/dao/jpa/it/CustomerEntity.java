package org.myshop.shop.dao.jpa.it;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.myshop.shop.model.Customer;

@Entity
@Table(name = "customer")
public class CustomerEntity extends Customer{

	@Id
	private String id;
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public Customer toCustomer() {
		Customer customer = new Customer();
		
		customer.setId(this.getId());
		customer.setName(this.getName());
		
		return customer;
	}
	
}
