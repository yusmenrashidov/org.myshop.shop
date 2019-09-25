package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

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
	
	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = Vendor.class.getFields();
		Field [] jpaEntityFields = VendorEntity.class.getFields();
		
		for(int i = 0; i < modelFields.length; i++) {
			
			modelFields[i].setAccessible(true);
			jpaEntityFields[i].setAccessible(true);
			
			try {		
				assertEquals(modelFields[i], jpaEntityFields[i]);
			
			}catch(AssertionError e) {
				System.out.print("There are unreflected fields: "+ modelFields[i].getName() + ", " + jpaEntityFields[i].getName());
			}
		}
	}
}
