package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderLineDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderLineDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class PurchaseOrderLineDeserializer implements IPurchaseOrderLineDeserializer {

	public PurchaseOrderLine deserialize(String jsonString) throws PurchaseOrderLineDeserializationException {
		
Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new PurchaseOrderLineDeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new PurchaseOrderLineDeserializationException("Input string is empty");
			}
			
			PurchaseOrderLine purchaseOrderLine = gson.fromJson(jsonString, PurchaseOrderLine.class);
			
			return purchaseOrderLine;
			
		}catch(JsonSyntaxException e) {
			throw new PurchaseOrderLineDeserializationException(e);
		}
	}

}
