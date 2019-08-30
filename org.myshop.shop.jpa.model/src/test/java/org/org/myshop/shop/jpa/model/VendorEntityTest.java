package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.Vendor;

public class VendorEntityTest {

	@Test
	public void testCreateVendorEntity() {
		
		Vendor vendor = new Vendor();
		
		vendor.setId("test_id");
		vendor.setName("test_name");
		
		VendorEntity vendorEntity = new VendorEntity(vendor);
		
		assertNotNull(vendorEntity);
		
		assertNotNull(vendorEntity.getId());
		assertNotNull(vendorEntity.getName());
		
		assertEquals(vendorEntity.getId(), "test_id");
		assertEquals(vendorEntity.getName(), "test_name");
		
		vendor = vendorEntity.toVendor();
		
		assertEquals(vendor.getId(), "test_id");
		assertEquals(vendor.getName(), "test_name");
	}
}
