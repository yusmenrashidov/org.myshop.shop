package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.myshop.shop.model.Item;

@Entity
@Table(name = "item")
@NamedQueries({
	@NamedQuery(name="item.read", query="SELECT item FROM ItemEntity item")	
})
public class ItemEntity {

	@Id
	private String id = "";
	private String name = "";
	private String description = "";
	@OneToOne
	private ProductGroupEntity productGroup;
	@OneToOne
	private ItemCategoryEntity itemCategory;
	private float purchasePrice;
	private float salesPrice;
	
	public ItemEntity() {
		super();
	}
	
	public ItemEntity(Item item) {
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.productGroup = new ProductGroupEntity(item.getProductGroup());
		this.itemCategory = new ItemCategoryEntity(item.getItemCategory());
		this.purchasePrice = item.getPurchasePrice();
		this.salesPrice = item.getSalesPrice();
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

	public ProductGroupEntity getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroupEntity productGroup) {
		this.productGroup = productGroup;
	}

	public ItemCategoryEntity getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategoryEntity itemCategory) {
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
	
	public Item toItem() {
		Item item = new Item();
		
		item.setId(getId());
		item.setDescription(getDescription());
		item.setName(getName());
		item.setProductGroup(productGroup.toProductGroup());
		item.setItemCategory(itemCategory.toItemCategory());
		item.setPurchasePrice(getPurchasePrice());
		item.setSalesPrice(getSalesPrice());
		
		return item;
	}
	
}
