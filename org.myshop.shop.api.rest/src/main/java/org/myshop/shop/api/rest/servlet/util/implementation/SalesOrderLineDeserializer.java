package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.SalesOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderLineDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderLineDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SalesOrderLineDeserializer implements ISalesOrderLineDeserializer{

	public SalesOrderLine deserialize(String jsonString) throws SalesOrderLineDeserializationException {
		
Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new SalesOrderLineDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new SalesOrderLineDeserializationException("Input string is empty");
			}
			
			SalesOrderLine salesOrderLine = gson.fromJson(jsonString, SalesOrderLine.class);
			
			return salesOrderLine;
		}catch(JsonSyntaxException e) {
			throw new SalesOrderLineDeserializationException(e);
		}		
	}
}
