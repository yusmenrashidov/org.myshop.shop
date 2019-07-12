package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public interface ProductGroupDao {
	
	public void addProductGroup(ProductGroup productGroup);
	public List<ProductGroup> getProductGroups();
	public List<ProductGroup> getProductGroupsByCategory(ItemCategory category);
	public List<ProductGroup> getProductGroupsByDescription(String description);
	public ProductGroup getProductGroup(String id);
	public void editProductGroup(String id);
	public void deleteProductGroup(String id);
	
}
