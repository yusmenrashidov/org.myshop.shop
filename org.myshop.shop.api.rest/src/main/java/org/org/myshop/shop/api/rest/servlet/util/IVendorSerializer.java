package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.Vendor;

public interface IVendorSerializer {
	
	public String serializeList(List<Vendor> vendorList);
}
