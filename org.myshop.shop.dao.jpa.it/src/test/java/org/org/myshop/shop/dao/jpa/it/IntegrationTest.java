package org.org.myshop.shop.dao.jpa.it;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class IntegrationTest {

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
		
	}
	
}
