package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.myshop.shop.model.ItemCategory;

@Entity
@Table(name = "itemCategory")
@NamedQueries({
	@NamedQuery(name="itemCategory.read", query="SELECT itemCategory FROM ItemCategoryEntity itemCategory")	
})
public class ItemCategoryEntity {

	@Id
	private String id = "";
	private String name = "";
	private String description = "";
	
	public ItemCategoryEntity() {
		super();
	}
	
	public ItemCategoryEntity(ItemCategory itemCategory) {
		this.id = itemCategory.getId();
		this.name = itemCategory.getName();
		this.description = itemCategory.getDescription();
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
	
	public ItemCategory toItemCategory() {
		ItemCategory itemCategory = new ItemCategory();
		
		itemCategory.setId(getId());
		itemCategory.setName(getName());
		itemCategory.setDescription(getDescription());
		
		return itemCategory;
	}
}
