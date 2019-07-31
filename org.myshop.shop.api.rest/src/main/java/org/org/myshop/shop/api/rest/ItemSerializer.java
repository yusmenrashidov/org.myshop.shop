package org.org.myshop.shop.api.rest;

import java.util.List;

import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.util.IItemSerializer;

import com.google.gson.Gson;

public class ItemSerializer implements IItemSerializer {

	public String serializeList(List<Item> itemList) {
		
		String listToJson = new Gson().toJson(itemList);
		
		return listToJson;
	}

}
