package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public interface ProductGroupDao {
	
	public void addProductGroup(ProductGroup productGroup);
	public List<ProductGroup> getProductGroups();
	public List<ProductGroup> getProductGroupsByCategory(ItemCategory category);
	public ProductGroup getProductGroup(String id);
	
	
}
