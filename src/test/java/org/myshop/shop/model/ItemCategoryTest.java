package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.myshop.shop.model.ItemCategory;

public class ItemCategoryTest {

	@Test
	public void test() {
		ItemCategory itemCategory = new ItemCategory();
		
		assertNotNull("Id should not be null on new ItemCategory", itemCategory.getId());
		assertEquals("Id should be empty on new ItemCategory", "", itemCategory.getId());
		
		assertNotNull("ItemName should not be null on new Item", itemCategory.getName());
		assertEquals("ItemNmae should be empty on new ItemCategory", "", itemCategory.getName());
		
		
		assertNotNull("Description should not be null on new ItemCategory", itemCategory.getDescription());
		assertEquals("Description shoud be empty on new ItemCategory", "", itemCategory.getDescription());
	}
}
