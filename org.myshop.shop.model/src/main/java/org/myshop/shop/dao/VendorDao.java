package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.Vendor;

public interface VendorDao {

	public void addVenor(Vendor vendor);
	public List<Vendor> getVendors();
	public List<Vendor> getVendorsByItemCategory(ItemCategory category);
	public Vendor getVendor(String id);
	public Vendor getVendorByName(String name);
	public void editVendor(Vendor vendor);
	public void deleteVendor(String id);

}
