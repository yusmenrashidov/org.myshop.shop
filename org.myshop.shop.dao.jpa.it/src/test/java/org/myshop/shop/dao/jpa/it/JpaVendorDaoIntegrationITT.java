package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaVendorDao;
import org.myshop.shop.model.Vendor;

public class JpaVendorDaoIntegrationITT {

	private JpaVendorDao vendorDao;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() {
		
		factory = Persistence.createEntityManagerFactory("myshopDB");
		
		vendorDao = new JpaVendorDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		Vendor vendor = new Vendor();
		
		vendor.setId("test_id");
		vendor.setName("test_name");
	
		vendorDao.create(vendor);
	}
	
	@Test
	public void testRead() {
		
		Vendor vendor = new Vendor();
		
		vendor.setId("id_1");
		vendor.setName("name_1");
		
		vendorDao.create(vendor);
		
		vendor = new Vendor();
		vendor.setId("id_2");
		vendor.setName("name_2");
		
		vendorDao.create(vendor);
		
		List<Vendor> readList = vendorDao.read();
		
		assertNotNull(readList);	
	}
	
	@Test
	public void testGet() {
		
		Vendor vendor = new Vendor();
		
		vendor.setId("test_id");
		vendor.setName("test_name");
		
		vendorDao.create(vendor);
		
		Vendor getVendor = vendorDao.get("test_id");
		
		assertNotNull(getVendor);
		
		assertEquals(getVendor.getId(), "test_id");
		assertEquals(getVendor.getName(), "test_name");
	}
	
	@Test
	public void testUpdate() {
		
		Vendor vendor = new Vendor();
		
		vendor.setId("test_id_update");
		vendor.setName("test_name");
		
		vendorDao.create(vendor);
		
		vendor.setName("test_update_name");
		
		vendor = vendorDao.update(vendor);
		
		vendor = vendorDao.get("test_id_update");
		
		assertEquals(vendor.getName(), "test_update_name");
	}
	
	@Test
	public void testDelete() {
		
		this.setup();
		
		Vendor vendor = new Vendor();
		
		vendor.setId("test_id_delete");
		vendor.setName("test_name");
		
		vendorDao.create(vendor);
		
		vendorDao.delete(vendor);
		
		vendor = vendorDao.get("test_id_delete");
		assertNull(vendor);
	}
}
