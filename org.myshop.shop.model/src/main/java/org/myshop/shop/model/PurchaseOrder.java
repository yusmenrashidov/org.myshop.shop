package org.myshop.shop.model;

import java.util.Date;

public class PurchaseOrder {

	private String id = "";
	private String number = "";
	private Date created;

	public PurchaseOrder() {
	}

	public PurchaseOrder(String id, String number, Date created) {
		this.id = id;
		this.number = number;
		this.created = created;
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

}
