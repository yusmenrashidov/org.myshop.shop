package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.api.rest.servlet.util.IProductGroupSerializer;

import com.google.gson.Gson;

public class ProductGroupSerializer implements IProductGroupSerializer{

	public String serialize(List<ProductGroup> productGroupList) {
			
		return  new Gson().toJson(productGroupList);
	}

}
