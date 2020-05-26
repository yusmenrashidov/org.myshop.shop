package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.myshop.shop.model.PurchaseOrderLine;

@Entity
@Table(name = "purchaseOrderLine")
@NamedQueries({
	@NamedQuery(name="purchaseOrderLine.read", query="SELECT purchaseOrderLine FROM PurchaseOrderLineEntity purchaseOrderLine")	
})
public class PurchaseOrderLineEntity {

	@Id
	private String id = "";
	private int lineNumber;
	@OneToOne
	private ItemEntity item;
	private int quantity;
	private float price;
	private int ammount;
	
	public PurchaseOrderLineEntity() {
		super();
	}
	
	public PurchaseOrderLineEntity(PurchaseOrderLine line) {
		
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
	
	public PurchaseOrderLine toPurchaseOrderLine() {
		
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		line.setId(getId());
		line.setLineNumber(getLineNumber());
		line.setItem(getItem().toItem());
		line.setQuantity(getQuantity());
		line.setPrice(getPrice());
		line.setAmount(getAmmount());
		
		return line;
	}
}
