package org.myshop.shop.api.rest.servlet.util.implementation;

import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.exc.VendorDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IVendorDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class VendorDeserializer implements IVendorDeserializer{

	public Vendor deserialize(String jsonString) throws VendorDeserializationException{
		
		Gson gson = new Gson();
		
		try {
			if(jsonString == null) {
				throw new VendorDeserializationException("Input string is null");

			}
			if(jsonString.isEmpty()) {
				throw new VendorDeserializationException("Input string is empty");
			}
			
			Vendor vendor = gson.fromJson(jsonString, Vendor.class);
			
			return vendor;
		}catch(JsonSyntaxException e ) {
			throw new VendorDeserializationException(e);
		}
	}
}
