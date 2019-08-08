package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.SalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderLineSerializer;

import com.google.gson.Gson;

public class SalesOrderLineSerializer implements ISalesOrderLineSerializer{

	public String serializerList(List<SalesOrderLine> salesOrderLineList) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(salesOrderLineList);		
		
		return listToJson;
	}

}
