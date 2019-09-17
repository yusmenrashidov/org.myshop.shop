package org.myshop.shop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.myshop.shop.model.Customer;
import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.jpa.model.CustomerEntity;
import org.org.myshop.shop.jpa.model.SalesOrderEntity;

public class JpaSalesOrderDaoTest {

	private final static String TEST_SALES_ORDER_ID = "TEST_SALES_ORDER_ID";
	private final static Date TEST_SALES_ORDER_CREATED = new Date(0);
	
	private static final String TEST_CUSTOMER_ID = "TEST_CUSTOMER_ID";
	private static final String TEST_CUSTOMER_NAME = "TEST_CUSTOMER_NAME";
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private SalesOrder orderMock;
	
	@Mock
	private SalesOrderEntity orderEntityMock;
	
	@Mock
	private Customer customerMock;
	
	@Mock
	private CustomerEntity customerEntityMock;
	
	@Mock
	private JpaSalesOrderDao salesOrderDaoMock;
	
	@Mock
	private List<SalesOrderEntity> entityListMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(entityManagerMock.createNamedQuery(JpaSalesOrderDao.READ_QUERY_NAME)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(SalesOrderEntity.class, TEST_SALES_ORDER_ID)).thenReturn(orderEntityMock);
		when(orderEntityMock.toSalesOrder()).thenReturn(orderMock);
		
		when(orderMock.getId()).thenReturn(TEST_SALES_ORDER_ID);
		when(orderMock.getCreated()).thenReturn(TEST_SALES_ORDER_CREATED);
		when(orderMock.getCustomer()).thenReturn(customerMock);
		
		when(customerMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		when(orderEntityMock.getId()).thenReturn(TEST_SALES_ORDER_ID);
		when(orderEntityMock.getCreated()).thenReturn(TEST_SALES_ORDER_CREATED);
		when(orderEntityMock.getCustomer()).thenReturn(customerEntityMock);
		
		when(customerEntityMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerEntityMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		salesOrderDaoMock = new JpaSalesOrderDao(factoryMock);
		
	}
	
	@Test
	public void testCreate() {
		salesOrderDaoMock.create(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<SalesOrderEntity> salesOrderEntityArgumentCaptor = ArgumentCaptor.forClass(SalesOrderEntity.class);
		verify(entityManagerMock).persist(salesOrderEntityArgumentCaptor.capture());
		
		SalesOrderEntity capturedSalesOrderEntity = salesOrderEntityArgumentCaptor.getValue();
		
		assertEquals(capturedSalesOrderEntity.getId(), TEST_SALES_ORDER_ID);
		assertEquals(capturedSalesOrderEntity.getCreated(), TEST_SALES_ORDER_CREATED);
		assertEquals(capturedSalesOrderEntity.getCustomer().getId(), TEST_CUSTOMER_ID);
		assertEquals(capturedSalesOrderEntity.getCustomer().getName(), TEST_CUSTOMER_NAME);
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<SalesOrder> orderList = salesOrderDaoMock.read();
		
		verify(entityManagerMock).createNamedQuery(JpaSalesOrderDao.READ_QUERY_NAME);
		verify(queryMock).getResultList();
		
		assertNotNull(orderList);
	}
	
	@Test
	public void testGet() {
		orderMock = salesOrderDaoMock.get(TEST_SALES_ORDER_ID);
		
		verify(entityManagerMock).find(SalesOrderEntity.class, TEST_SALES_ORDER_ID);
		
		assertNotNull(orderMock);
		
		assertEquals(orderMock.getId(), TEST_SALES_ORDER_ID);
		assertEquals(orderMock.getCreated(), TEST_SALES_ORDER_CREATED);
		assertEquals(orderMock.getCustomer().getId(), TEST_CUSTOMER_ID);
		assertEquals(orderMock.getCustomer().getName(), TEST_CUSTOMER_NAME);
	}
	
	@Test
	public void testUpdate() {
		orderMock = salesOrderDaoMock.update(orderMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		salesOrderDaoMock.delete(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<SalesOrderEntity> salesOrderEntityArgumentCaptor = ArgumentCaptor.forClass(SalesOrderEntity.class);
		verify(entityManagerMock).remove(salesOrderEntityArgumentCaptor.capture());
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(SalesOrderEntity.class, TEST_SALES_ORDER_ID)).thenThrow(new NullPointerException());
		
		orderMock = salesOrderDaoMock.get(TEST_SALES_ORDER_ID);
		
		assertNull(orderMock);
	}
}
