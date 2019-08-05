package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.ItemCategory;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IItemCategorySerializer {

	public String serializerList(List<ItemCategory> itemCategoryList) throws JsonProcessingException;
	
}
