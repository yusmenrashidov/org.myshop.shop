package org.org.myshop.shop.jpa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.myshop.shop.model.PurchaseOrder;

@Entity
@Table(name = "purchaseOrder")
@NamedQueries({
	@NamedQuery(name="purchaseOrder.read", query="SELECT purchaseOrder FROM PurchaseOrderEntity purchaseOrder")	
})
public class PurchaseOrderEntity {

	@Id
	private String id = "";
	private String number = "";
	private Date created;
	
	public PurchaseOrderEntity() {
		super();
	}
	
	public PurchaseOrderEntity(PurchaseOrder order) {
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
	
	public PurchaseOrder toPurchaseOrder() {
		PurchaseOrder order = new PurchaseOrder();
		
		order.setId(getId());
		order.setNumber(getNumber());
		order.setCreated(getCreated());
		
		return order;
	}
}
