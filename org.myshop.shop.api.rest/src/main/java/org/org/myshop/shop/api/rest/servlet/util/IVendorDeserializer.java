package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.Vendor;
import org.org.myshop.shop.api.rest.servlet.exc.VendorDeserializationException;

public interface IVendorDeserializer {
	
	public Vendor deserialize(String jsonString) throws VendorDeserializationException;
}
