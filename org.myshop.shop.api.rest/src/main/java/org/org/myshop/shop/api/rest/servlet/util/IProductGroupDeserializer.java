package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.api.rest.servlet.exc.ProductGroupDeserializationException;

public interface IProductGroupDeserializer {

	public ProductGroup deserialize(String productGroupJsonString) throws ProductGroupDeserializationException;
	
}
