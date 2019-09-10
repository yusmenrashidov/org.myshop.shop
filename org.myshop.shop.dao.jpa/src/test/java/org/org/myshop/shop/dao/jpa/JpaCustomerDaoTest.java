package org.org.myshop.shop.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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
	private Query queryMock;
	
	@Mock
	private List<CustomerEntity> entityListMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.createNativeQuery(JpaCustomerDao.READ_QUERY, CustomerEntity.class)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(CustomerEntity.class, TEST_CUSTOMER_ID)).thenReturn(customerEntityMock);
		when(customerEntityMock.toCustomer()).thenReturn(customerMock);
		
		when(customerMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		when(customerEntityMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerEntityMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
				
		customerDaoMock = new JpaCustomerDao(factoryMock);
		customerEntityMock = new CustomerEntity(customerMock);
	}
	
	@Test
	public void testCreate() {
		customerDaoMock.create(customerMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<Customer> customerList = customerDaoMock.read();
		
		verify(entityManagerMock).createNativeQuery(JpaCustomerDao.READ_QUERY, CustomerEntity.class);
		verify(queryMock).getResultList();
		
		assertNotNull(customerList);
	}
	
	@Test
	public void testGet() {
		customerMock = customerDaoMock.get(TEST_CUSTOMER_ID);
		
		verify(entityManagerMock).find(CustomerEntity.class, TEST_CUSTOMER_ID);
		
		assertNotNull(customerMock);
		
		assertEquals(customerMock.getId(), TEST_CUSTOMER_ID);
		assertEquals(customerMock.getName(), TEST_CUSTOMER_NAME);
	}
	
	@Test
	public void testUpdate() {
		customerDaoMock.update(customerMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		customerDaoMock.delete(customerMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(CustomerEntity.class, TEST_CUSTOMER_ID)).thenThrow(new NullPointerException());
		
		customerMock = customerDaoMock.get(TEST_CUSTOMER_ID);
		
		assertNull(customerMock);
	}
}
