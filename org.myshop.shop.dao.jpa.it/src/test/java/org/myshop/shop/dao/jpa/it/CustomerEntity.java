package org.myshop.shop.dao.jpa.it;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.myshop.shop.model.Customer;

@Entity
@Table(name = "customer")
public class CustomerEntity{

	@Id
	private String id;
	private String name;
	
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
	
	
	
}
