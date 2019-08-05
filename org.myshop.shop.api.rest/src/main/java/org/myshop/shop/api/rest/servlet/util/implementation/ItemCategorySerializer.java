package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategorySerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

public class ItemCategorySerializer implements IItemCategorySerializer{

	public String serializerList(List<ItemCategory> itemCategoryList) throws JsonProcessingException {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(itemCategoryList);
		
		return listToJson;
	}
	
	
}
