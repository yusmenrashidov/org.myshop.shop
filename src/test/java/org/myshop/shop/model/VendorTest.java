package org.myshop.shop.model;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.myshop.shop.model.Vendor;

public class VendorTest {
	@Test
	public void testItemFieldsNotNullOnNewItem() {
		Vendor vendor = new Vendor();
		
		assertNotNull(vendor.getId());
		assertNotNull(vendor.getName());
	
	}

}
