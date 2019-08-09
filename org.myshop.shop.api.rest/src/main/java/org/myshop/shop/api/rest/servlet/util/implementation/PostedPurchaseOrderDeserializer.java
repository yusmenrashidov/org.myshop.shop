package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.PostedPurchaseOrder;

import org.org.myshop.shop.api.rest.servlet.exc.PostedPurchaseOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPostedPurchaseOrderDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class PostedPurchaseOrderDeserializer implements IPostedPurchaseOrderDeserializer{

	public PostedPurchaseOrder deserialize(String jsonString) throws PostedPurchaseOrderDeserializationException {
		
Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new PostedPurchaseOrderDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new PostedPurchaseOrderDeserializationException("Input string is empty");
			}
			
			PostedPurchaseOrder postedPurchaseOrder = gson.fromJson(jsonString, PostedPurchaseOrder.class);
			
			return postedPurchaseOrder;
		}catch(JsonSyntaxException e) {
			throw new PostedPurchaseOrderDeserializationException(e);
		}
	}

}
