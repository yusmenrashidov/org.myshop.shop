package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Vendor;

public interface VendorDao {

	public void create(Vendor vendor);
	
	public List<Vendor> read();
	
	public Vendor get(String id);
	
	public Vendor update(Vendor vendor);
	
	public void delete(Vendor vendor);

}
