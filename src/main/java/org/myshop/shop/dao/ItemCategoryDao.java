package org.myshop.shop.dao;

import org.myshop.shop.model.ItemCategory;
import java.util.List;

public interface ItemCategoryDao {

	public void addItemCategory(ItemCategory itemCategory);
	public List<ItemCategory> getCategories();
	public ItemCategory getCategory(String id);
	public void editCategory(String id);
	public void deleteCategory(String id);
	
}
