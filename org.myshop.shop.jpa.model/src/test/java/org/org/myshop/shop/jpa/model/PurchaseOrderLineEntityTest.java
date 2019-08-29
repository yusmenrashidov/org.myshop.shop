package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.PurchaseOrderLine;
import org.myshop.shop.model.ProductGroup;

public class PurchaseOrderLineEntityTest {

	@Test
	public void testCreatePurchaseOrderLineEntity() {
		
		ItemCategory category = new ItemCategory();
		category.setId("test_item_category_id");
		
		ProductGroup group = new ProductGroup();
		group.setId("test_product_group_id");
		group.setItemCategory(category);
		
		Item item = new Item();
		
		item.setId("test_item_id");
		item.setDescription("test_item_description");
		item.setItemCategory(category);
		item.setProductGroup(group);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		PurchaseOrderLine line = new PurchaseOrderLine();		
		
		line.setId("test_line_id");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		
		PurchaseOrderLineEntity entity = new PurchaseOrderLineEntity(line);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), line.getId());
		assertEquals(entity.getItem().getId(), line.getItem().getId());
		assertEquals(entity.getLineNumber(), line.getLineNumber());
		assertEquals(0f, entity.getPrice(), line.getPrice());
		assertEquals(entity.getQuantity(), line.getQuantity());
	}
}
