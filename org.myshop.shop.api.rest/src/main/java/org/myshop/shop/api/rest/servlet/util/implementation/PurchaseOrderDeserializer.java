package org.myshop.shop.api.rest.servlet.util.implementation;


import org.myshop.shop.model.PurchaseOrder;

import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class PurchaseOrderDeserializer implements IPurchaseOrderDeserializer {

	public PurchaseOrder deserialize(String jsonString) throws PurchaseOrderDeserializationException {
		
Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new PurchaseOrderDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new PurchaseOrderDeserializationException("Input string is empty");
			}
			
			PurchaseOrder purchaseOrder = gson.fromJson(jsonString, PurchaseOrder.class);
			
			return purchaseOrder;
		}catch(JsonSyntaxException e) {
			throw new PurchaseOrderDeserializationException(e);
		}
	}
}
