package org.org.myshop.shop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

public class JpaItemDaoTest {

	private final static String TEST_PRODUCT_GROUP_ID = "TEST_PRODUCT_GROUP_ID";
	private final static String TEST_PRODUCT_GROUP_DESCRIPTION = "TEST_PRODUCT_GROUP_DESCRIPTION";

	private final static String TEST_ITEM_CATEGORY_ID = "TEST_ITEM_CATEGORY_ID";
	private final static String TEST_ITEM_CATEGORY_NAME = "TEST_ITEM_CATEGORY_NAME";
	private final static String TEST_ITEM_CATEGORY_DESCRIPTION = "TEST_ITEM_CATEGORY_DESCRIPTION";
	
	private final static String TEST_ITEM_ID = "TEST_ITEM_ID";
	private final static String TEST_ITEM_NAME = "TEST_ITEM_NAME";
	private final static String TEST_ITEM_DESCRIPTION = "TEST_ITEM_DESCRIPTION";
	private final static float TEST_PURCHASE_PRICE = 123.456f;
	private final static float TEST_SALES_PRICE = 123.789f;
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private Item itemMock;
	
	@Mock
	private ItemEntity itemEntityMock;
	
	@Mock
	private ProductGroupEntity productGroupEntityMock;

	@Mock
	private ProductGroup productGroupMock;

	@Mock
	private ItemCategoryEntity itemCategoryEntityMock;

	@Mock
	private ItemCategory itemCategoryMock;
	
	@Mock
	private JpaItemDao itemDaoMock;
	
	@Mock
	private List<ItemEntity> entityListMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(entityManagerMock.createNativeQuery(JpaItemDao.READ_QUERY, ItemEntity.class)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(ItemEntity.class, TEST_ITEM_ID)).thenReturn(itemEntityMock);
		when(itemEntityMock.toItem()).thenReturn(itemMock);
		
		when(productGroupMock.getId()).thenReturn(TEST_PRODUCT_GROUP_ID);
		when(productGroupMock.getDescription()).thenReturn(TEST_PRODUCT_GROUP_DESCRIPTION);
		when(productGroupMock.getItemCategory()).thenReturn(itemCategoryMock);

		when(productGroupEntityMock.getId()).thenReturn(TEST_PRODUCT_GROUP_ID);
		when(productGroupEntityMock.getDescription()).thenReturn(TEST_PRODUCT_GROUP_DESCRIPTION);
		when(productGroupEntityMock.getItemCategory()).thenReturn(itemCategoryEntityMock);

		when(itemCategoryMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(itemCategoryMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
		when(itemCategoryMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);

		when(itemCategoryEntityMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(itemCategoryEntityMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
		when(itemCategoryEntityMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
		
		when(itemMock.getId()).thenReturn(TEST_ITEM_ID);
		when(itemMock.getName()).thenReturn(TEST_ITEM_NAME);
		when(itemMock.getDescription()).thenReturn(TEST_ITEM_DESCRIPTION);
		when(itemMock.getItemCategory()).thenReturn(itemCategoryMock);
		when(itemMock.getProductGroup()).thenReturn(productGroupMock);
		when(itemMock.getPurchasePrice()).thenReturn(TEST_PURCHASE_PRICE);
		when(itemMock.getSalesPrice()).thenReturn(TEST_SALES_PRICE);
		
		when(itemEntityMock.getId()).thenReturn(TEST_ITEM_ID);
		when(itemEntityMock.getName()).thenReturn(TEST_ITEM_NAME);
		when(itemEntityMock.getDescription()).thenReturn(TEST_ITEM_DESCRIPTION);
		when(itemEntityMock.getItemCategory()).thenReturn(itemCategoryEntityMock);
		when(itemEntityMock.getProductGroup()).thenReturn(productGroupEntityMock);
		when(itemEntityMock.getPurchasePrice()).thenReturn(TEST_PURCHASE_PRICE);
		when(itemEntityMock.getSalesPrice()).thenReturn(TEST_SALES_PRICE);
	
		itemDaoMock = new JpaItemDao(factoryMock);
		
		itemEntityMock = new ItemEntity(itemMock);
		productGroupEntityMock = new ProductGroupEntity(productGroupMock);
		itemCategoryEntityMock = new ItemCategoryEntity(itemCategoryMock);
	}
	
	@Test
	public void testCreate() {
		itemDaoMock.create(itemMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<Item> itemList = itemDaoMock.read();
		
		verify(entityManagerMock).createNativeQuery(JpaItemDao.READ_QUERY, ItemEntity.class);
		verify(queryMock).getResultList();
		
		assertNotNull(itemList);
	}
	
	@Test
	public void testGet() {
		Item item = itemDaoMock.get(TEST_ITEM_ID);
		
		verify(entityManagerMock).find(ItemEntity.class, TEST_ITEM_ID);
		
		assertNotNull(item);
		
		assertEquals(item.getId(), TEST_ITEM_ID);
		assertEquals(item.getName(), TEST_ITEM_NAME);
		assertEquals(item.getDescription(), TEST_ITEM_DESCRIPTION);
		assertEquals(item.getProductGroup().getId(), TEST_PRODUCT_GROUP_ID);
		assertEquals(item.getItemCategory().getId(), TEST_ITEM_CATEGORY_ID);
		assertEquals(0f, item.getPurchasePrice(), 123.456f);
		assertEquals(0f, item.getSalesPrice(), 123.789f);
	}
	
	@Test
	public void testUpdate() {
		Item item = itemDaoMock.update(itemMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
		
		assertNotNull(item);
	}
	
	@Test
	public void testDelete() {
		itemDaoMock.delete(itemMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(ItemEntity.class, TEST_ITEM_ID)).thenThrow(new NullPointerException());
		
		Item item = itemDaoMock.get(TEST_ITEM_ID);
		
		assertNull(item);
	}
}
