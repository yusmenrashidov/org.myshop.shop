package org.myshop.shop.dao.jpa;

import java.util.Date;
import java.util.List;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.jpa.model.PurchaseOrderEntity;

public class JpaPurchaseOrderDaoTest {

	
	private final static String TEST_PURCHASE_ORDER_ID = "TEST_PURCHASE_ORDER_ID";
	private final static String TEST_PURCHASE_ORDER_NUMBER = "TEST_PURCHASE_ORDER_NUMBER";
	private final static Date TEST_PURCHASE_ORDER_CREATED = new Date(0);
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private PurchaseOrder orderMock;
	
	@Mock
	private PurchaseOrderEntity entityMock;
	
	@Mock
	private JpaPurchaseOrderDao purchaseOrderDaoMock;
	
	@Mock
	private List<PurchaseOrderEntity> entityListMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.createNativeQuery(JpaPurchaseOrderDao.READ_QUERY, PurchaseOrderEntity.class)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(PurchaseOrderEntity.class, TEST_PURCHASE_ORDER_ID)).thenReturn(entityMock);
		when(entityMock.toPurchaseOrder()).thenReturn(orderMock);
		
		when(entityMock.getId()).thenReturn(TEST_PURCHASE_ORDER_ID);
		when(entityMock.getNumber()).thenReturn(TEST_PURCHASE_ORDER_NUMBER);
		when(entityMock.getCreated()).thenReturn(TEST_PURCHASE_ORDER_CREATED);
		
		when(orderMock.getId()).thenReturn(TEST_PURCHASE_ORDER_ID);
		when(orderMock.getNumber()).thenReturn(TEST_PURCHASE_ORDER_NUMBER);
		when(orderMock.getCreated()).thenReturn(TEST_PURCHASE_ORDER_CREATED);
		
		purchaseOrderDaoMock = new JpaPurchaseOrderDao(factoryMock);
		
	}
	
	@Test
	public void testCreate() {
		purchaseOrderDaoMock.create(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PurchaseOrderEntity> purchaseOrderEntityArgumentCaptor = ArgumentCaptor.forClass(PurchaseOrderEntity.class);
		verify(entityManagerMock).persist(purchaseOrderEntityArgumentCaptor.capture());
		
		PurchaseOrderEntity capturedPurchaseOrderEntity = purchaseOrderEntityArgumentCaptor.getValue();
		
		assertEquals(capturedPurchaseOrderEntity.getId(), TEST_PURCHASE_ORDER_ID);
		assertEquals(capturedPurchaseOrderEntity.getNumber(), TEST_PURCHASE_ORDER_NUMBER);
		assertEquals(capturedPurchaseOrderEntity.getCreated(), TEST_PURCHASE_ORDER_CREATED);
	
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<PurchaseOrder> orderList = purchaseOrderDaoMock.read();
		
		verify(entityManagerMock).createNativeQuery(JpaPurchaseOrderDao.READ_QUERY, PurchaseOrderEntity.class);
		verify(queryMock).getResultList();
		
		assertNotNull(orderList);
	}
	
	@Test
	public void testGet() {
		orderMock = purchaseOrderDaoMock.get(TEST_PURCHASE_ORDER_ID);
		
		verify(entityManagerMock).find(PurchaseOrderEntity.class, TEST_PURCHASE_ORDER_ID);
		
		assertNotNull(orderMock);
		
		assertEquals(orderMock.getId(), TEST_PURCHASE_ORDER_ID);
		assertEquals(orderMock.getNumber(), TEST_PURCHASE_ORDER_NUMBER);
		assertEquals(orderMock.getCreated(), TEST_PURCHASE_ORDER_CREATED);
	}
	
	@Test
	public void testUpdate() {
		orderMock = purchaseOrderDaoMock.update(orderMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		purchaseOrderDaoMock.delete(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PurchaseOrderEntity> purchaseOrderEntityArgumentCaptor = ArgumentCaptor.forClass(PurchaseOrderEntity.class);
		verify(entityManagerMock).remove(purchaseOrderEntityArgumentCaptor.capture());
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(PurchaseOrderEntity.class, TEST_PURCHASE_ORDER_ID)).thenThrow(new NullPointerException());
		
		orderMock = purchaseOrderDaoMock.get(TEST_PURCHASE_ORDER_ID);
	
		assertNull(orderMock);
	}
	
}
