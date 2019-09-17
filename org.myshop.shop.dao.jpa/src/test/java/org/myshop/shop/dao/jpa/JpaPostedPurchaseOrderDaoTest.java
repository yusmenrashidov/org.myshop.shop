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

import org.myshop.shop.model.PostedPurchaseOrder;
import org.org.myshop.shop.jpa.model.PostedPurchaseOrderEntity;

public class JpaPostedPurchaseOrderDaoTest {

	
	private final static String TEST_POSTED_PURCHASE_ORDER_ID = "TEST_POSTED_PURCHASE_ORDER_ID";
	private final static String TEST_POSTED_PURCHASE_ORDER_NUMBER = "TEST_POSTED_PURCHASE_ORDER_NUMBER";
	private final static Date TEST_POSTED_PURCHASE_ORDER_CREATED = new Date(0);
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private PostedPurchaseOrder orderMock;
	
	@Mock
	private PostedPurchaseOrderEntity entityMock;
	
	@Mock
	private JpaPostedPurchaseOrderDao postedPurchaseOrderDaoMock;
	
	@Mock
	private List<PostedPurchaseOrderEntity> entityListMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.createNamedQuery(JpaPostedPurchaseOrderDao.READ_QUERY_NAME)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(PostedPurchaseOrderEntity.class, TEST_POSTED_PURCHASE_ORDER_ID)).thenReturn(entityMock);
		when(entityMock.toPostedPurchaseOrder()).thenReturn(orderMock);
		
		when(entityMock.getId()).thenReturn(TEST_POSTED_PURCHASE_ORDER_ID);
		when(entityMock.getNumber()).thenReturn(TEST_POSTED_PURCHASE_ORDER_NUMBER);
		when(entityMock.getCreated()).thenReturn(TEST_POSTED_PURCHASE_ORDER_CREATED);
		
		when(orderMock.getId()).thenReturn(TEST_POSTED_PURCHASE_ORDER_ID);
		when(orderMock.getNumber()).thenReturn(TEST_POSTED_PURCHASE_ORDER_NUMBER);
		when(orderMock.getCreated()).thenReturn(TEST_POSTED_PURCHASE_ORDER_CREATED);
		
		postedPurchaseOrderDaoMock = new JpaPostedPurchaseOrderDao(factoryMock);
		
	}
	
	@Test
	public void testCreate() {
		postedPurchaseOrderDaoMock.create(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PostedPurchaseOrderEntity> postedPurchaseOrderEntityArgumentCaptor = ArgumentCaptor.forClass(PostedPurchaseOrderEntity.class);
		verify(entityManagerMock).persist(postedPurchaseOrderEntityArgumentCaptor.capture());
		
		PostedPurchaseOrderEntity capturedPostedPurchaseOrderEntity = postedPurchaseOrderEntityArgumentCaptor.getValue();
		
		assertEquals(capturedPostedPurchaseOrderEntity.getId(), TEST_POSTED_PURCHASE_ORDER_ID);
		assertEquals(capturedPostedPurchaseOrderEntity.getNumber(), TEST_POSTED_PURCHASE_ORDER_NUMBER);
		assertEquals(capturedPostedPurchaseOrderEntity.getCreated(), TEST_POSTED_PURCHASE_ORDER_CREATED);
	
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<PostedPurchaseOrder> orderList = postedPurchaseOrderDaoMock.read();
		
		verify(entityManagerMock).createNamedQuery(JpaPostedPurchaseOrderDao.READ_QUERY_NAME);
		verify(queryMock).getResultList();
		
		assertNotNull(orderList);
	}
	
	@Test
	public void testGet() {
		orderMock = postedPurchaseOrderDaoMock.get(TEST_POSTED_PURCHASE_ORDER_ID);
		
		verify(entityManagerMock).find(PostedPurchaseOrderEntity.class, TEST_POSTED_PURCHASE_ORDER_ID);
		
		assertNotNull(orderMock);
		
		assertEquals(orderMock.getId(), TEST_POSTED_PURCHASE_ORDER_ID);
		assertEquals(orderMock.getNumber(), TEST_POSTED_PURCHASE_ORDER_NUMBER);
		assertEquals(orderMock.getCreated(), TEST_POSTED_PURCHASE_ORDER_CREATED);
	}
	
	@Test
	public void testUpdate() {
		orderMock = postedPurchaseOrderDaoMock.update(orderMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		postedPurchaseOrderDaoMock.delete(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PostedPurchaseOrderEntity> postedPurchaseOrderEntityArgumentCaptor = ArgumentCaptor.forClass(PostedPurchaseOrderEntity.class);
		verify(entityManagerMock).remove(postedPurchaseOrderEntityArgumentCaptor.capture());
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(PostedPurchaseOrderEntity.class, TEST_POSTED_PURCHASE_ORDER_ID)).thenThrow(new NullPointerException());
		
		orderMock = postedPurchaseOrderDaoMock.get(TEST_POSTED_PURCHASE_ORDER_ID);
	
		assertNull(orderMock);
	}
	
}
