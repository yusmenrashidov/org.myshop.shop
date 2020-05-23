package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.myshop.shop.model.PostedPurchaseOrderLine;

@Entity
@Table(name = "postedPurchaseOrderLine")
@NamedQueries({
	@NamedQuery(name="postedPurchaseOrderLine.read", query="SELECT postedPurchaseOrderLine FROM PostedPurchaseOrderLineEntity postedPurchaseOrderLine")	
})
public class PostedPurchaseOrderLineEntity {

	@Id
	private String id = "";
	private int lineNumber;
	@OneToOne
	private ItemEntity item;
	private int quantity;
	private float price;
	private int ammount;
	
	public PostedPurchaseOrderLineEntity() {
		super();
	}
	
	public PostedPurchaseOrderLineEntity(PostedPurchaseOrderLine line) {
		
		this.id = line.getId();
		this.lineNumber = line.getLineNumber();
		this.item = new ItemEntity(line.getItem());
		this.quantity = line.getQuantity();
		this.price = line.getPrice();
		this.ammount = line.getAmount();
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
	
	public PostedPurchaseOrderLine toPostedPurchaseOrderLine() {
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
		
		line.setId(getId());
		line.setLineNumber(getLineNumber());
		line.setItem(getItem().toItem());
		line.setQuantity(getQuantity());
		line.setPrice(getPrice());
		line.setAmount(getAmmount());
		
		return line;
	}
}
