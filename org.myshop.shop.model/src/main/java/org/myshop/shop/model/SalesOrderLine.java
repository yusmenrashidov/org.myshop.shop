package org.myshop.shop.model;

public class SalesOrderLine {

	private String id = "";
	private int lineNumber;
	private Item item;
	private int quantity;
	private float price;
	private int amount;

	public SalesOrderLine() {
	}

	public SalesOrderLine(String id, int lineNumber, Item item, int quantity, float price, int amount) {
		this.id = id;
		this.lineNumber = lineNumber;
		this.item = item;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
