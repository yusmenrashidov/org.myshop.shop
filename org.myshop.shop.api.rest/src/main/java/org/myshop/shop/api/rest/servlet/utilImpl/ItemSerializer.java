package org.myshop.shop.api.rest.servlet.utilImpl;

import java.util.List;

import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.util.IItemSerializer;

import com.google.gson.Gson;

public class ItemSerializer implements IItemSerializer {

	public String serializeList(List<Item> itemList) {
		
		Gson gson = new Gson(); 
		
		String listToJson = gson.toJson(itemList);
		
		return listToJson;
	}

}
