package org.myshop.shop.dao;

import org.myshop.shop.model.ItemCategory;
import java.util.List;

public interface ItemCategoryDao {

	public void create(ItemCategory category);
	
	public List<ItemCategory> read();
	
	public ItemCategory get(String id);
	
	public ItemCategory update(ItemCategory category);
	
	public void delete(ItemCategory category);
}
