package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.ItemCategory;

public class ItemCategoryEntityTest {

	
	@Test
	public void testCreateItemCategoryEntity() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_id");
		category.setDescription("test_description");
		category.setName("test_name");
		
		ItemCategoryEntity entity = new ItemCategoryEntity(category);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), "test_id");
		assertEquals(entity.getDescription(), "test_description");
		assertEquals(entity.getName(), "test_name");
		
	}
}
