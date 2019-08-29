package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.myshop.shop.model.PostedSalesOrderLine;

@Entity
@Table(name = "postedSalesOrderLine")
public class PostedSalesOrdeLineEntity {

	@Id
	private String id = "";
	private int lineNumber;
	@OneToOne
	private ItemEntity item;
	private int quantity;
	private float price;
	private int ammount;
	
	public PostedSalesOrdeLineEntity() {
		super();
	}
	
	public PostedSalesOrdeLineEntity(PostedSalesOrderLine line) {
		
		this.id = line.getId();
		this.lineNumber = line.getLineNumber();
		this.item = new ItemEntity(line.getItem());
		this.quantity = line.getQuantity();
		this.price = line.getPrice();
		this.ammount = line.getAmmount();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public ItemEntity getItem() {
		return item;
	}

	public void setItem(ItemEntity item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}
	
	public PostedSalesOrderLine toPostedSalesOrderLine() {
		
		PostedSalesOrderLine line = new PostedSalesOrderLine();
		
		line.setId(getId());
		line.setLineNumber(getLineNumber());
		line.setItem(getItem().toItem());
		line.setQuantity(getQuantity());
		line.setPrice(getPrice());
		line.setAmmount(getAmmount());
		
		return line;
	}	
}
