package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaSalesOrderLineDao;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.SalesOrderLine;

public class JpaSalesOrderLineDaoIntegrationIT extends BaseItemAwareIntegrationTest {

	private JpaSalesOrderLineDao salesOrderLineDao;
	
	@Before
	public void setup() {
		super.setup();
		
		salesOrderLineDao = new JpaSalesOrderLineDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		Item item = itemDao.get("test_item_id"); 	
		
		SalesOrderLine line = new SalesOrderLine();
		
		line.setId("test_id");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		salesOrderLineDao.create(line);
	}
	
	@Test
	public void testRead() {
		
		Item item = itemDao.get("test_item_id");
		
		SalesOrderLine line = new SalesOrderLine();
		
		line.setId("test_id_1");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		salesOrderLineDao.create(line);
		
		line.setId("test_id_2");
		line.setItem(item);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmmount(321);
		
		salesOrderLineDao.create(line);
		
		List<SalesOrderLine> lineList = salesOrderLineDao.read();
		
		assertNotNull(lineList);
	}
	
	@Test
	public void testGet() {
		
		Item item = itemDao.get("test_item_id");
		
		SalesOrderLine line = new SalesOrderLine();
		
		line.setId("test_id_get");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		salesOrderLineDao.create(line);
		
		line = salesOrderLineDao.get("test_id_get");
		
		assertNotNull(line);
		assertEquals(line.getId(), "test_id_get");
		assertEquals(line.getItem().getId(), "test_item_id");
		assertEquals(line.getLineNumber(), 123);
		assertEquals(0f, line.getPrice(), 123.456f);
		assertEquals(line.getQuantity(), 123);
		assertEquals(line.getAmmount(), 123);
	}
	
	@Test
	public void testUpdate() {
		
		Item item = itemDao.get("test_item_id");
		Item itemToUpdate = itemDao.get("test_item_id_2");
		
		SalesOrderLine line = new SalesOrderLine();
		
		line.setId("test_id_update");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		salesOrderLineDao.create(line);
		
		line.setItem(itemToUpdate);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmmount(321);
		
		line = salesOrderLineDao.update(line);
		
		assertEquals(line.getId(), "test_id_update");
		assertEquals(line.getItem().getId(), "test_item_id_2");
		assertEquals(line.getLineNumber(), 321);
		assertEquals(0f, line.getPrice(), 123.789f);
		assertEquals(line.getQuantity(), 321);
		assertEquals(line.getAmmount(), 321);
	}
	
	@Test
	public void testDelete() {
		
		Item item = itemDao.get("test_item_id");
		
		SalesOrderLine line = new SalesOrderLine();
		
		line.setId("test_id_delete");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		salesOrderLineDao.create(line);
		
		salesOrderLineDao.delete(line);
		
		line = salesOrderLineDao.get("test_id_delete");
		
		assertNull(line);
	}
}
