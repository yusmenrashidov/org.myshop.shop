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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;
import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;
import org.org.myshop.shop.jpa.model.PurchaseOrderEntity;
import org.org.myshop.shop.jpa.model.PurchaseOrderLineEntity;

public class JpaPurchaseOrderLineDaoTest {

	private final static String TEST_PURCHASE_ORDER_LINE_ID = "TEST_PURCHASE_ORDER_LINE_ID";
	private final static int TEST_PURCHASE_ORDER_LINE_LINE_NUMBER = 123;
	private final static int TEST_PURCHASE_ORDER_LINE_QUANTITY = 456;
	private final static float TEST_PURCHASE_ORDER_LINE_PRICE = 123.456f;
	private final static int TEST_PURCHASE_ORDER_LINE_AMMOUNT = 789;
	
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
	private JpaPurchaseOrderLineDao purchaseOrderLineDaoMock;
	
	@Mock
	private PurchaseOrderLine lineMock;
	
	@Mock
	private PurchaseOrderLineEntity entityMock;
	
	@Mock
	private Item itemMock;
	
	@Mock
	private ItemEntity itemEntityMock;
	
	@Mock
	private ProductGroup productGroupMock;
	
	@Mock
	private ProductGroupEntity productGroupEntityMock;
	
	@Mock
	private ItemCategory categoryMock;
	
	@Mock
	private ItemCategoryEntity categoryEntityMock;
	
	@Mock
	private List<PurchaseOrderEntity> entityListMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.createNamedQuery(JpaPurchaseOrderLineDao.READ_QUERY_NAME)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(PurchaseOrderLineEntity.class, TEST_PURCHASE_ORDER_LINE_ID)).thenReturn(entityMock);
		when(entityMock.toPurchaseOrderLine()).thenReturn(lineMock);
		
		when(productGroupMock.getId()).thenReturn(TEST_PRODUCT_GROUP_ID);
		when(productGroupMock.getDescription()).thenReturn(TEST_PRODUCT_GROUP_DESCRIPTION);
		when(productGroupMock.getItemCategory()).thenReturn(categoryMock);

		when(productGroupEntityMock.getId()).thenReturn(TEST_PRODUCT_GROUP_ID);
		when(productGroupEntityMock.getDescription()).thenReturn(TEST_PRODUCT_GROUP_DESCRIPTION);
		when(productGroupEntityMock.getItemCategory()).thenReturn(categoryEntityMock);

		when(categoryMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(categoryMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
		when(categoryMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);

		when(categoryEntityMock.getId()).thenReturn(TEST_ITEM_CATEGORY_ID);
		when(categoryEntityMock.getName()).thenReturn(TEST_ITEM_CATEGORY_NAME);
		when(categoryEntityMock.getDescription()).thenReturn(TEST_ITEM_CATEGORY_DESCRIPTION);
		
		when(itemMock.getId()).thenReturn(TEST_ITEM_ID);
		when(itemMock.getName()).thenReturn(TEST_ITEM_NAME);
		when(itemMock.getDescription()).thenReturn(TEST_ITEM_DESCRIPTION);
		when(itemMock.getItemCategory()).thenReturn(categoryMock);
		when(itemMock.getProductGroup()).thenReturn(productGroupMock);
		when(itemMock.getPurchasePrice()).thenReturn(TEST_PURCHASE_PRICE);
		when(itemMock.getSalesPrice()).thenReturn(TEST_SALES_PRICE);
		
		when(itemEntityMock.getId()).thenReturn(TEST_ITEM_ID);
		when(itemEntityMock.getName()).thenReturn(TEST_ITEM_NAME);
		when(itemEntityMock.getDescription()).thenReturn(TEST_ITEM_DESCRIPTION);
		when(itemEntityMock.getItemCategory()).thenReturn(categoryEntityMock);
		when(itemEntityMock.getProductGroup()).thenReturn(productGroupEntityMock);
		when(itemEntityMock.getPurchasePrice()).thenReturn(TEST_PURCHASE_PRICE);
		when(itemEntityMock.getSalesPrice()).thenReturn(TEST_SALES_PRICE);
		
		when(lineMock.getId()).thenReturn(TEST_PURCHASE_ORDER_LINE_ID);
		when(lineMock.getLineNumber()).thenReturn(TEST_PURCHASE_ORDER_LINE_LINE_NUMBER);
		when(lineMock.getItem()).thenReturn(itemMock);
		when(lineMock.getQuantity()).thenReturn(TEST_PURCHASE_ORDER_LINE_QUANTITY);
		when(lineMock.getPrice()).thenReturn(TEST_PURCHASE_ORDER_LINE_PRICE);
		when(lineMock.getAmmount()).thenReturn(TEST_PURCHASE_ORDER_LINE_AMMOUNT);
		
		when(entityMock.getId()).thenReturn(TEST_PURCHASE_ORDER_LINE_ID);
		when(entityMock.getLineNumber()).thenReturn(TEST_PURCHASE_ORDER_LINE_LINE_NUMBER);
		when(entityMock.getItem()).thenReturn(itemEntityMock);
		when(entityMock.getQuantity()).thenReturn(TEST_PURCHASE_ORDER_LINE_QUANTITY);
		when(entityMock.getPrice()).thenReturn(TEST_PURCHASE_ORDER_LINE_PRICE);
		when(entityMock.getAmmount()).thenReturn(TEST_PURCHASE_ORDER_LINE_AMMOUNT);
		
		purchaseOrderLineDaoMock = new JpaPurchaseOrderLineDao(factoryMock);
	}
	
	@Test
	public void testCreate() {
		purchaseOrderLineDaoMock.create(lineMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PurchaseOrderLineEntity> purchaseOrderLineEntityArgumentCaptor = ArgumentCaptor.forClass(PurchaseOrderLineEntity.class);
		verify(entityManagerMock).persist(purchaseOrderLineEntityArgumentCaptor.capture());

		PurchaseOrderLineEntity capturedPurchaseOrderLineEntity = purchaseOrderLineEntityArgumentCaptor.getValue();
		
		assertEquals(capturedPurchaseOrderLineEntity.getId(), TEST_PURCHASE_ORDER_LINE_ID);
		assertEquals(capturedPurchaseOrderLineEntity.getItem().getId(), TEST_ITEM_ID);
		assertEquals(capturedPurchaseOrderLineEntity.getLineNumber(), TEST_PURCHASE_ORDER_LINE_LINE_NUMBER);
		assertEquals(0f, capturedPurchaseOrderLineEntity.getPrice(), TEST_PURCHASE_ORDER_LINE_PRICE);
		assertEquals(capturedPurchaseOrderLineEntity.getQuantity(), TEST_PURCHASE_ORDER_LINE_QUANTITY);
		assertEquals(capturedPurchaseOrderLineEntity.getAmmount(), TEST_PURCHASE_ORDER_LINE_AMMOUNT);
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<PurchaseOrderLine> lineList = purchaseOrderLineDaoMock.read();
		
		verify(entityManagerMock).createNamedQuery(JpaPurchaseOrderLineDao.READ_QUERY_NAME);
		verify(queryMock).getResultList();
		
		assertNotNull(lineList);
	}
	
	@Test
	public void testGet() {
		lineMock = purchaseOrderLineDaoMock.get(TEST_PURCHASE_ORDER_LINE_ID);
		
		verify(entityManagerMock).find(PurchaseOrderLineEntity.class, TEST_PURCHASE_ORDER_LINE_ID);
		
		assertNotNull(lineMock);
	
		assertEquals(lineMock.getId(), TEST_PURCHASE_ORDER_LINE_ID);
		assertEquals(lineMock.getItem().getId(), TEST_ITEM_ID);
		assertEquals(lineMock.getLineNumber(), TEST_PURCHASE_ORDER_LINE_LINE_NUMBER);
		assertEquals(0f, lineMock.getPrice(), TEST_PURCHASE_ORDER_LINE_PRICE);
		assertEquals(lineMock.getQuantity(), TEST_PURCHASE_ORDER_LINE_QUANTITY);
		assertEquals(lineMock.getAmmount(), TEST_PURCHASE_ORDER_LINE_AMMOUNT);
	}
	
	@Test
	public void testUpdate() {
		lineMock = purchaseOrderLineDaoMock.update(lineMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		purchaseOrderLineDaoMock.delete(lineMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PurchaseOrderLineEntity> purchaseOrderLineEntityArgumentCaptor = ArgumentCaptor.forClass(PurchaseOrderLineEntity.class);
		verify(entityManagerMock).remove(purchaseOrderLineEntityArgumentCaptor.capture());

		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(PurchaseOrderLineEntity.class, TEST_PURCHASE_ORDER_LINE_ID)).thenThrow(new NullPointerException());
		
		lineMock = purchaseOrderLineDaoMock.get(TEST_PURCHASE_ORDER_LINE_ID);
		
		assertNull(lineMock);
	}
}
