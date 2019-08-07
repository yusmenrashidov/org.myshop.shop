package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.SalesOrder;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderSerializer;

import com.google.gson.Gson;

public class SalesOrderSerializer implements ISalesOrderSerializer{

	public String serializerList(List<SalesOrder> salesOrderList) {

		Gson gson = new Gson();
		
		String listToJson = gson.toJson(salesOrderList);		
		
		return listToJson;
	}
	
}
