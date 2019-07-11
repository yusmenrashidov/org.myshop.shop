package org.myshop.shop.dao;

import org.myshop.shop.model.ItemCategory;
import java.util.List;

public interface ItemCategoryDao {

	public void addItemCategory(ItemCategory itemCategory);
	public List<ItemCategory> getCategories();
	public ItemCategory getCategory(String id);
	
}
