package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.api.rest.servlet.exc.ItemCategoryDeserializationException;

public interface IItemCategoryDeserializer {
	
	public ItemCategory deserialize(String jsonString) throws ItemCategoryDeserializationException;
}
