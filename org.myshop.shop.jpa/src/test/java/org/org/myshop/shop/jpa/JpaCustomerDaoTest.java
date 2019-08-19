package org.org.myshop.shop.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import org.myshop.shop.model.Customer;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaCustomerDaoTest {

	private static final String TEST_CUSTOMER_ID = "test_customer_id";
	private static final String TEST_CUSTOMER_NAME = "test_customer_name";
	
	private static final String TEST_PERSISTENCE_UNIT_NAME = "test_persistence_unit_name";
	
	@Mock
	private JpaCustomerDao jpaCustomerDao;
	
	@Mock
	private Customer customerMock;
	
	@Mock
	private List<Customer> customerListMock;
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(Persistence.createEntityManagerFactory(TEST_PERSISTENCE_UNIT_NAME)).thenReturn(factoryMock);
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		
		when(entityManagerMock.createQuery(JpaCustomerDao.READ_QUERY).getResultList()).thenReturn(customerListMock);
		when(entityManagerMock.find(Customer.class, TEST_CUSTOMER_ID)).thenReturn(customerMock);
		
		when(customerMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		jpaCustomerDao = new JpaCustomerDao(factoryMock);
	}
	
	@Test
	public void test_create() {
	}
	
}
