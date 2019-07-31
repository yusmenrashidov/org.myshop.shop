package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.Item;

public interface IItemSerializer {
	
	public String serializeList(List<Item> itemList);
	
}
