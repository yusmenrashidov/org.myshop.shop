package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.ISalesOrderDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SalesOrderDeserializer implements ISalesOrderDeserializer{

	public SalesOrder deserialize(String jsonString) throws SalesOrderDeserializationException {
		
		Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new SalesOrderDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new SalesOrderDeserializationException("Input string is empty");
			}
			
			SalesOrder salesOrder = gson.fromJson(jsonString, SalesOrder.class);
			
			return salesOrder;
		}catch(JsonSyntaxException e) {
			throw new SalesOrderDeserializationException(e);
		}
	}

}
