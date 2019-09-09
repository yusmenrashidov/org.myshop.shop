package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.myshop.shop.model.ProductGroup;

@Entity
@Table(name = "productGroup")
public class ProductGroupEntity {

	@Id
	private String id = "";
	private String description;
	
	@OneToOne
	private ItemCategoryEntity itemCategory;
	
	
	public ProductGroupEntity() {
		super();
	}
	
	public ProductGroupEntity(ProductGroup productGroup) {
		this.id = productGroup.getId();
		this.description = productGroup.getDescription();
		this.itemCategory = new ItemCategoryEntity(productGroup.getItemCategory());
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

	public ItemCategoryEntity getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategoryEntity itemCategory) {
		this.itemCategory = itemCategory;
	}
	
	public ProductGroup toProductGroup() {
		ProductGroup productGroup = new ProductGroup();
		
		productGroup.setId(getId());
		productGroup.setDescription(getDescription());
		productGroup.setItemCategory(getItemCategory().toItemCategory());
		
		return productGroup;
	}
}
