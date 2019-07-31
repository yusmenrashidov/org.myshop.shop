package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.Item;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IItemSerializer {
	
	public String serializeList(List<Item> itemList) throws JsonProcessingException;
	
}
