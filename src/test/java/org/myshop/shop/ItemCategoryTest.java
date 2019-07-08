package org.myshop.shop;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.ItemCategory;

public class ItemCategoryTest {

	@Test
	public void test() {
		ItemCategory itemCategory = new ItemCategory();
		
		assertNotNull(itemCategory.getId());
		assertNotNull(itemCategory.getName());
		assertNotNull(itemCategory.getDescription());
	}
}
