package org.myshop.shop.model;

public class ProductGroup {

	private String id = "";
	private String description;
	private ItemCategory itemCategory;

	public ProductGroup() {
	}

	public ProductGroup(String id, String description, ItemCategory itemCategory) {
		this.id = id;
		this.description = description;
		this.itemCategory = itemCategory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

}
