package org.myshop.shop.api.rest.servlet.utilImpl;

import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.api.rest.servlet.exc.ProductGroupDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IProductGroupDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ProductGroupDeserializer implements IProductGroupDeserializer{

	public ProductGroup deserialize(String jsonString) throws ProductGroupDeserializationException{
		
		Gson gson = new Gson();
		
		try {
		
		if(jsonString == null) {
			throw new ProductGroupDeserializationException("input string is null");
		}
			
		if(jsonString.isEmpty()) {
			throw new ProductGroupDeserializationException("input string is empty");
		}
	
		ProductGroup productGroup = gson.fromJson(jsonString, ProductGroup.class);
		return productGroup;
		
		}catch(JsonSyntaxException e) {
			throw new ProductGroupDeserializationException(e);
		}
	}

}
