package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.PostedSalesOrder;
import org.org.myshop.shop.api.rest.servlet.exc.PostedSalesOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPostedSalesOrderDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class PostedSalesOrderDeserializer implements IPostedSalesOrderDeserializer{

	public PostedSalesOrder deserialize(String jsonString) throws PostedSalesOrderDeserializationException {
		
		Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new PostedSalesOrderDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new PostedSalesOrderDeserializationException("Input string is empty");
			}
			
			PostedSalesOrder postedSalesOrder = gson.fromJson(jsonString, PostedSalesOrder.class);
			
			return postedSalesOrder;
		}catch(JsonSyntaxException e) {
			throw new PostedSalesOrderDeserializationException(e);
		}
	}

}
