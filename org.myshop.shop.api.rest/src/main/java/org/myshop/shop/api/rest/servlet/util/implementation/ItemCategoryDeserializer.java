package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.api.rest.servlet.exc.ItemCategoryDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategoryDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ItemCategoryDeserializer implements IItemCategoryDeserializer{

	public ItemCategory deserialize(String jsonString) throws ItemCategoryDeserializationException{
		
		Gson gson = new Gson();
		
		try {
		if(jsonString == null) {
			throw new ItemCategoryDeserializationException("Input string is null");
		}
		if(jsonString.isEmpty()) {
			throw new ItemCategoryDeserializationException("Input string is empty");
		}
		
		ItemCategory itemCategory = gson.fromJson(jsonString, ItemCategory.class);
		
		return itemCategory;
		}catch(JsonSyntaxException e) {
			throw new ItemCategoryDeserializationException(e);
		}
		
	}
	
}
