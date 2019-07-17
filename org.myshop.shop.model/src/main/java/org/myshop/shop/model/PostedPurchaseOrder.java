package org.myshop.shop.model;

import java.util.Date;

public class PostedPurchaseOrder {

	private String id = "";
	private String number = "";
	private Date created;
	
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
