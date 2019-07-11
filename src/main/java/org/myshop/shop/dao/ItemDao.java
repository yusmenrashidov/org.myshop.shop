package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public interface ItemDao {
	
	public void addItem(Item item);
	public List<Item> getItems();
	public List<Item> getItemsByProductGroup(ProductGroup productGroup);
	public List<Item> getItemsByItemCategory(ItemCategory category);
	public Item getItem(String id);
	

}
