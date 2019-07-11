package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.Vendor;

public interface VendorDao {

	public void addVenor(Vendor vendor);
	public List<Vendor> getVendors();
	public Vendor getVendor(Vendor vendor);
	public List<Vendor> getVendorsByItemCategory(ItemCategory category);
	public void editVendor(Vendor vendor);

}
