package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import org.myshop.shop.model.Customer;
import org.org.myshop.shop.dao.jpa.JpaCustomerDao;

public class JpaCustomerDaoIntegrationIT {

	private JpaCustomerDao customerDao;
	
	@Before
	public void setup() {
		customerDao = new JpaCustomerDao();
	}
	
	@Test
	public void testCreate() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id");
		customer.setName("test_name");
	
		customerDao.create(customer);
	}

	@Test
	public void testGet() {
	
		Customer customer = new Customer();
		
		customer.setId("test_id");
		customer.setName("test_name");
		
		customerDao.create(customer);
		
		Customer getCustomer = customerDao.get("test_id");
		
		assertNotNull(getCustomer);
		assertEquals(getCustomer.getId(), "test_id");
		assertEquals(getCustomer.getName(), "test_name");
	}
	
	@Test
	public void testUpdate() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id_update");
		customer.setName("test_name");
		
		customerDao.create(customer);
		
		customer.setName("test_update_name");
		
		customer = customerDao.update(customer);
		
		customer = customerDao.get("test_id_update");
		
		assertEquals(customer.getName(), "test_update_name");
	}
	
	@Test
	public void testDelete() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id_delete");
		customer.setName("test_name");
		
		customerDao.create(customer);
		
		customerDao.delete(customer);
		
		customer = customerDao.get("test_id_delete");
		assertNull(customer);
	}
	
}
