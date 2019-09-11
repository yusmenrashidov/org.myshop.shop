package org.org.myshop.shop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;

public class JpaProductGroupDaoTest {

	private final static String TEST_PRODUCT_GROUP_ID = "TEST_PRODUCT_GROUP_ID";
	private final static String TEST_PRODUCT_GROUP_DESCRIPTION = "TEST_PRODUCT_GROUP_DESCRIPTION";
	
	private final static String TEST_ITEM_CATEGORY_ID = "TEST_ITEM_CATEGORY_ID";
	private final static String TEST_ITEM_CATEGORY_NAME = "TEST_ITEM_CATEGORY_NAME";
	private final static String TEST_ITEM_CATEGORY_DESCRIPTION = "TEST_ITEM_CATEGORY_DESCRIPTION";
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private List<ProductGroup> groupListMock;
	
	@Mock
	private ProductGroupEntity productGroupEntityMock;
	
	@Mock
	private ProductGroup productGroupMock;
	
	@Mock
	private ItemCategoryEntity itemCategoryEntityMock;
	
	@Mock
	private ItemCategory itemCategoryMock;
	
	@Mock
	private JpaProductGroupDao productGroupDaoMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(entityManagerMock.createNativeQuery(JpaProductGroupDao.READ_QUERY, ProductGroupEntity.class)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(groupListMock);
		
		when(entityManagerMock.find(ProductGroupEntity.class, TEST_PRODUCT_GROUP_ID)).thenReturn(productGroupEntityMock);
		when(productGroupEntityMock.toProductGroup()).thenReturn(productGroupMock);
	
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
		
		productGroupDaoMock = new JpaProductGroupDao(factoryMock);
		
		itemCategoryEntityMock = new ItemCategoryEntity(itemCategoryMock);
		productGroupEntityMock = new ProductGroupEntity(productGroupMock);
		
	}
	
	@Test
	public void testCreate() {
		productGroupDaoMock.create(productGroupMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<ProductGroup> productGroupList = productGroupDaoMock.read();
		
		verify(entityManagerMock).createNativeQuery(JpaProductGroupDao.READ_QUERY, ProductGroupEntity.class);
		verify(queryMock).getResultList();
		
		assertNotNull(productGroupList);
	}
	
	@Test
	public void testGet() {
		ProductGroup productGroup = productGroupDaoMock.get(TEST_PRODUCT_GROUP_ID);
		
		verify(entityManagerMock).find(ProductGroupEntity.class, TEST_PRODUCT_GROUP_ID);
		
		assertNotNull(productGroup);
		
		assertEquals(productGroup.getId(), TEST_PRODUCT_GROUP_ID);
		assertEquals(productGroup.getDescription(), TEST_PRODUCT_GROUP_DESCRIPTION);
		assertEquals(productGroup.getItemCategory().getId(), TEST_ITEM_CATEGORY_ID);
		assertEquals(productGroup.getItemCategory().getName(), TEST_ITEM_CATEGORY_NAME);
		assertEquals(productGroup.getItemCategory().getDescription(), TEST_ITEM_CATEGORY_DESCRIPTION);	
	}
	
	@Test
	public void testUpdate() {
		ProductGroup productGroup = productGroupDaoMock.update(productGroupMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
		
		assertNotNull(productGroup);
	}
	
	
	@Test
	public void testDelete() {
		productGroupDaoMock.delete(productGroupMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(ProductGroupEntity.class, TEST_PRODUCT_GROUP_ID)).thenThrow(new NullPointerException());
		
		productGroupMock = productGroupDaoMock.get(TEST_PRODUCT_GROUP_ID);
		
		assertNull(productGroupMock);
	}
}
