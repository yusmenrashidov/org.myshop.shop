package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.ProductGroup;

public interface IProductGroupSerializer {

	public String serialize(List<ProductGroup> productGroupList);
	
}
