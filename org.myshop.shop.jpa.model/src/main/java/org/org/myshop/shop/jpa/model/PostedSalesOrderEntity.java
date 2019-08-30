package org.org.myshop.shop.jpa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.myshop.shop.model.PostedSalesOrder;

@Entity
@Table(name = "postedSalesOrder")
public class PostedSalesOrderEntity {

	@Id
	private String id = "";
	private Date created;
	@OneToOne
	private CustomerEntity customer;
	
	public PostedSalesOrderEntity() {
		super();
	}
	
	public PostedSalesOrderEntity(PostedSalesOrder order) {
		this.id = order.getId();
		this.created = order.getCreated();
		this.customer = new CustomerEntity(order.getCustomer());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	
	public PostedSalesOrder toPostedSalesOrder() {
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId(getId());
		order.setCreated(getCreated());
		order.setCustomer(customer.toCustomer());
		
		return order;
	}
	
}
