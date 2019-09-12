package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.myshop.shop.model.Vendor;

public class VendorTest {
	@Test
	public void testItemFieldsNotNullOnNewItem() {
		Vendor vendor = new Vendor();

		assertNotNull("Id should not be null on new Vendor", vendor.getId());
		assertEquals("Id should be empty on new Vendor", "", vendor.getId());

		assertNotNull("Name should not be null on new Vendor", vendor.getName());
		assertEquals("Name should be empty on new Vendor", "", vendor.getName());
	}

}
