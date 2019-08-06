package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.util.IVendorSerializer;

import com.google.gson.Gson;

public class VendorSerializer implements IVendorSerializer {

	public String serializeList(List<Vendor> vendorList) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(vendorList);
		
		return listToJson;
	}

}
