package org.org.myshop.shop.jpa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.myshop.shop.model.PostedPurchaseOrder;

@Entity
@Table(name = "postedPurchaseOrder")
public class PostedPurchaseOrderEntity {

	@Id
	private String id = "";
	private String number = "";
	private Date created;
	
	public PostedPurchaseOrderEntity() {
		super();
	}
	
	public PostedPurchaseOrderEntity(PostedPurchaseOrder order) {
		this.id = order.getId();
		this.number = order.getNumber();
		this.created = order.getCreated();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public PostedPurchaseOrder toPostedPurchaseOrder() {
		PostedPurchaseOrder order = new PostedPurchaseOrder();
		
		order.setId(getId());
		order.setNumber(getNumber());
		order.setCreated(getCreated());
		
		return order;
	}
}
