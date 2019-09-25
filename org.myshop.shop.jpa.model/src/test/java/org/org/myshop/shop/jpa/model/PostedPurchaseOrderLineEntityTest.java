package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.PostedPurchaseOrderLine;
import org.myshop.shop.model.ProductGroup;

public class PostedPurchaseOrderLineEntityTest {

	@Test
	public void testCreatePostedPurchaseOrderLineEntity() {
		
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
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();		
		
		line.setId("test_line_id");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		
		PostedPurchaseOrderLineEntity entity = new PostedPurchaseOrderLineEntity(line);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), line.getId());
		assertEquals(entity.getItem().getId(), line.getItem().getId());
		assertEquals(entity.getLineNumber(), line.getLineNumber());
		assertEquals(0f, entity.getPrice(), line.getPrice());
		assertEquals(entity.getQuantity(), line.getQuantity());
		
	}
	
	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = PostedPurchaseOrderLine.class.getFields();
		Field [] jpaEntityFields = PostedPurchaseOrderLineEntity.class.getFields();
		
		for(int i = 0; i < modelFields.length; i++) {
			
			modelFields[i].setAccessible(true);
			jpaEntityFields[i].setAccessible(true);
			
			try {		
				assertEquals(modelFields[i], jpaEntityFields[i]);
			
			}catch(AssertionError e) {
				System.out.print("There are unreflected fields: "+ modelFields[i].getName() + ", " + jpaEntityFields[i].getName());
			}
		}
	}
}
