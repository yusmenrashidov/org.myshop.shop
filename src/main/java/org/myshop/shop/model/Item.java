package org.myshop.shop.model;

public class Item {
	
	private String id="";
	private String name="";
	private String description="";
	private ProductGroup productGroup;
	private ItemCategory itemCategory;
	
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

}
