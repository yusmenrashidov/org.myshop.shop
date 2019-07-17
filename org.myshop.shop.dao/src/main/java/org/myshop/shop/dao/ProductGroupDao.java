package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.ProductGroup;

public interface ProductGroupDao {
	
	public void create(ProductGroup productGroup);
	
	public List<ProductGroup> read();
	
	public ProductGroup get(String id);
	
	public ProductGroup update(ProductGroup productGroup);
	
	public void delete(ProductGroup productGroup);
	
}
