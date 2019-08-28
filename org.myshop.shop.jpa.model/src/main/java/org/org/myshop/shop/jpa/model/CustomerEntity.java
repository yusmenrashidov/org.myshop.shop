package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.myshop.shop.model.Customer;

@Entity
@Table(name = "customer")
public class CustomerEntity{

	@Id
	private String id = "";
	private String name = "";
	
	public CustomerEntity(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
	}
	
	public CustomerEntity() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Customer toCustomer() {
		Customer customer = new Customer();
		
		customer.setId(getId());
		customer.setName(getName());
		
		return customer;
	}
}
