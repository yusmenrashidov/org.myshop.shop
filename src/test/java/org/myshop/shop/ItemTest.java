package org.myshop.shop;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.myshop.shop.model.Item;

public class ItemTest {

	@Test
	public void testItemFieldsNotNullOnNewItem() {
		Item item = new Item();
		
		assertNotNull(item.getId());
		assertNotNull(item.getName());
		assertNotNull(item.getDescription());
		assertNull(item.getItemCategory());
		assertTrue(item.getPurchasePrice()==0);
		assertTrue(item.getSalesPrice()==0);
	}
}
