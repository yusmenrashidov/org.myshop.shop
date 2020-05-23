package org.myshop.shop.model;

public class Item {

	private String id = "";
	private String name = "";
	private String description = "";
	private ProductGroup productGroup;
	private ItemCategory itemCategory;
	private float purchasePrice;
	private float salesPrice;

	public Item() {}

	public Item(String id, String name, String description, ProductGroup productGroup, ItemCategory itemCategory, float purchasePrice, float salesPrice) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.productGroup = productGroup;
		this.itemCategory = itemCategory;
		this.purchasePrice = purchasePrice;
		this.salesPrice = salesPrice;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public float getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(float salesPrice) {
		this.salesPrice = salesPrice;
	}

}
