package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

public class IntegrationTestIT {

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() throws Exception{
		    
		factory = Persistence.createEntityManagerFactory("TestCustomer");
		entityManager = factory.createEntityManager();
	}
	
	@Test
	public void testInsertSomeData() {
		
		entityManager.getTransaction().begin();
		
		CustomerEntity customer = new CustomerEntity();
		customer.setId("test_id");
		customer.setName("test_name");
		
		entityManager.persist(customer);	
		
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void testGetData() {
		
		CustomerEntity customer = entityManager.find(CustomerEntity.class, "test_id");
		
		assertNotNull(customer);
		assertEquals(customer.getId(), "test_id");
		assertEquals(customer.getName(), "test_name");
	}
	
	@Test
	public void testUpdate() {
		CustomerEntity customer = entityManager.find(CustomerEntity.class, "test_id");
		
		entityManager.getTransaction().begin();
		
		customer.setName("test_update_name");
		
		entityManager.getTransaction().commit();
	
	    customer = entityManager.find(CustomerEntity.class, "test_id");
	    
	    assertEquals(customer.getId(), "test_id");
	    assertEquals(customer.getName(), "test_update_name");
	
	}
	
	@Test
	public void testDelete() {
		CustomerEntity customer = entityManager.find(CustomerEntity.class, "test_id");
		
		entityManager.getTransaction().begin();
		entityManager.remove(customer);
		entityManager.getTransaction().commit();
		
		customer = entityManager.find(CustomerEntity.class, "test_id");
		assertNull(customer);
	}
	
	
}
