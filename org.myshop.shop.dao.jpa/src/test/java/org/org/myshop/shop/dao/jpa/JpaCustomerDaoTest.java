package org.org.myshop.shop.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transaction;

import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.model.Customer;
import org.org.myshop.shop.dao.jpa.JpaCustomerDao;
import org.org.myshop.shop.jpa.model.CustomerEntity;

public class JpaCustomerDaoTest {

	private static final String TEST_CUSTOMER_ID = "TEST_CUSTOMER_ID";
	private static final String TEST_CUSTOMER_NAME = "TEST_CUSTOMER_NAME";
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private CustomerEntity customerEntityMock;
	
	@Mock
	private Customer customerMock;
	
	@Mock
	private JpaCustomerDao customerDaoMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		//when(Persistence.createEntityManagerFactory("myshopDB")).thenReturn(factoryMock);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		
		when(customerMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		when(customerEntityMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerEntityMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		customerDaoMock = new JpaCustomerDao(factoryMock);
	}
	
	@Test
	public void testCreate() {
		customerDaoMock.create(customerMock);
	}
	
}
