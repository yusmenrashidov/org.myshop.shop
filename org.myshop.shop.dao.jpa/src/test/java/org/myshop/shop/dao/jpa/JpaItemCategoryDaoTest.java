package org.myshop.shop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.myshop.shop.dao.jpa.JpaItemCategoryDao;
import org.myshop.shop.model.ItemCategory;

import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ItemEntity;

public class JpaItemCategoryDaoTest {

	private final static String TEST_ITEM_CATEGORY_ID = "TEST_ITEM_CATEGORY_ID";
	private final static String TEST_ITEM_CATEGORY_NAME = "TEST_ITEM_CATEGORY_NAME";
	private final static String TEST_ITEM_CATEGORY_DESCRIPTION = "TEST_ITEM_CATEGORY_DESCRIPTION";
	
	@Mock
	private JpaItemCategoryDao itemCategoryDaoMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private ItemCategory itemCategoryMock;
	
	@Mock
	private ItemCategoryEntity itemCategoryEntityMock;

	@Mock
	private Query queryMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.createNamedQuery(JpaItemCategoryDao.READ_QUERY_NAME)).thenReturn(queryMock);;
		
		when(entityManagerMock.find(ItemCategoryEntity.class, TEST_ITEM_CATEGORY_ID)).thenReturn(itemCategoryEntityMock);
		when(itemCategoryEntityMock.toItemCategory()).thenReturn(itemCategoryMock);
		
		when(itemCategoryMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(itemCategoryMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
		when(itemCategoryMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
		
		when(itemCategoryEntityMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(itemCategoryEntityMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
		when(itemCategoryEntityMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
		
		itemCategoryDaoMock = new JpaItemCategoryDao(factoryMock);
		itemCategoryEntityMock = new ItemCategoryEntity(itemCategoryMock);
	}
	
	
	@Test
	public void testCreate() {
		itemCategoryDaoMock.create(itemCategoryMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<ItemCategoryEntity> itemCategoryEntityArgumentCaptor = ArgumentCaptor.forClass(ItemCategoryEntity.class);
		verify(entityManagerMock).persist(itemCategoryEntityArgumentCaptor.capture());
		
		ItemCategoryEntity capturedItemCategoryEntity = itemCategoryEntityArgumentCaptor.getValue();
		
		assertEquals(capturedItemCategoryEntity.getId(), TEST_ITEM_CATEGORY_ID);
		assertEquals(capturedItemCategoryEntity.getName(), TEST_ITEM_CATEGORY_NAME);
		assertEquals(capturedItemCategoryEntity.getDescription(), TEST_ITEM_CATEGORY_DESCRIPTION);
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		when(queryMock.getResultList()).thenReturn(EMPTY_LIST);

		List<ItemCategory> itemCategoryListMock = itemCategoryDaoMock.read();
		
		verify(entityManagerMock).createNamedQuery(JpaItemCategoryDao.READ_QUERY_NAME);
		verify(queryMock).getResultList();
		
		assertNotNull(itemCategoryListMock);
		
	}
	
	@Test
	public void testGet() {
		itemCategoryMock = itemCategoryDaoMock.get(TEST_ITEM_CATEGORY_ID);
		
		verify(entityManagerMock).find(ItemCategoryEntity.class, TEST_ITEM_CATEGORY_ID);
		
		assertNotNull(itemCategoryMock);
		
		assertEquals(itemCategoryMock.getId(), TEST_ITEM_CATEGORY_ID);
		assertEquals(itemCategoryMock.getName(), TEST_ITEM_CATEGORY_NAME);
		assertEquals(itemCategoryMock.getDescription(), TEST_ITEM_CATEGORY_DESCRIPTION);
	}
	
	@Test
	public void testUpdate() {
		itemCategoryMock = itemCategoryDaoMock.update(itemCategoryMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		itemCategoryDaoMock.delete(itemCategoryMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<ItemCategoryEntity> itemCategoryEntityArgumentCaptor = ArgumentCaptor.forClass(ItemCategoryEntity.class);
		verify(entityManagerMock).remove(itemCategoryEntityArgumentCaptor.capture());
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(ItemCategoryEntity.class, TEST_ITEM_CATEGORY_ID)).thenThrow(new NullPointerException());
		
		itemCategoryMock = itemCategoryDaoMock.get(TEST_ITEM_CATEGORY_ID);
		
		assertNull(itemCategoryMock);
	}
	
	@Test
	public void testRead_failed() {
		when(queryMock.getResultList()).thenThrow(new PersistenceException());
		
		List<ItemCategory> itemCategoryList = itemCategoryDaoMock.read();
		
		assertNull(itemCategoryList);
	}
}
