package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.PostedSalesOrder;
import org.org.myshop.shop.api.rest.servlet.util.IPostedSalesOrderSerializer;

import com.google.gson.Gson;

public class PostedSalesOrderSerializer implements IPostedSalesOrderSerializer{

	public String serializeLis(List<PostedSalesOrder> postedSalesOrderList) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(postedSalesOrderList);
		
		return listToJson;
	}

}
