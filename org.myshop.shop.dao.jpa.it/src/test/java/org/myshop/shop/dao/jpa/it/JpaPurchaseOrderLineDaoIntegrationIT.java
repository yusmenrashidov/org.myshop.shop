package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaPurchaseOrderLineDao;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.PurchaseOrderLine;

public class JpaPurchaseOrderLineDaoIntegrationIT extends BaseItemAwareIntegrationTest {

	private JpaPurchaseOrderLineDao purchaseOrderLineDao;
	
	@Before
	public void setup() {
		super.setup();
		
		purchaseOrderLineDao = new JpaPurchaseOrderLineDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		System.out.println("testCreate, begin");
		
		Item item = itemDao.get("test_item_id"); 	
		
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		line.setId("test_id");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmount(123);
		
		purchaseOrderLineDao.create(line);
		
		System.out.println("testCreate, end");
	}
	
	@Test
	public void testRead() {
		
		Item item = itemDao.get("test_item_id");
		
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		line.setId("test_id_1");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmount(123);
		
		purchaseOrderLineDao.create(line);
		
		line.setId("test_id_2");
		line.setItem(item);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmount(321);
		
		purchaseOrderLineDao.create(line);
		
		List<PurchaseOrderLine> lineList = purchaseOrderLineDao.read();
		
		assertNotNull(lineList);
	}
	
	@Test
	public void testGet() {
		
		Item item = itemDao.get("test_item_id");
		
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		line.setId("test_id_get");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmount(123);
		
		purchaseOrderLineDao.create(line);
		
		line = purchaseOrderLineDao.get("test_id_get");
		
		assertNotNull(line);
		assertEquals(line.getId(), "test_id_get");
		assertEquals(line.getItem().getId(), "test_item_id");
		assertEquals(line.getLineNumber(), 123);
		assertEquals(0f, line.getPrice(), 123.456f);
		assertEquals(line.getQuantity(), 123);
		assertEquals(line.getAmount(), 123);
	}
	
	@Test
	public void testUpdate() {
		
		List<Item> itemList = itemDao.read();
		
		System.out.println("===============================================================================================================================================");
		System.out.println("item list before update test: " + itemList.size());
		
		Item item = itemDao.get("test_item_id");
		Item itemToUpdate = itemDao.get("test_item_id_2");
		
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		line.setId("test_id_update");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmount(123);
		
		purchaseOrderLineDao.create(line);
		
		line.setItem(itemToUpdate);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmount(321);
		
		line = purchaseOrderLineDao.update(line);
		
		assertEquals(line.getId(), "test_id_update");
		assertEquals(line.getItem().getId(), "test_item_id_2");
		assertEquals(line.getLineNumber(), 321);
		assertEquals(0f, line.getPrice(), 123.789f);
		assertEquals(line.getQuantity(), 321);
		assertEquals(line.getAmount(), 321);
	}
	
	@Test
	public void testDelete() {
		
		Item item = itemDao.get("test_item_id");
		
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		line.setId("test_id_delete");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmount(123);
		
		purchaseOrderLineDao.create(line);
		
		purchaseOrderLineDao.delete(line);
		
		line = purchaseOrderLineDao.get("test_id_delete");
		
		assertNull(line);
	}
}
