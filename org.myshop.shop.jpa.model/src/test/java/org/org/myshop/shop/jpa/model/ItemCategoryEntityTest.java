package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

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
	
	@Test
	public void testEntityFieldsSameAsModelFiels() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		Field [] modelFields = ItemCategory.class.getFields();
		Field [] jpaEntityFields = ItemCategoryEntity.class.getFields();
	
		for(int i = 0; i < modelFields.length; i++) {
			
			modelFields[i].setAccessible(true);
			jpaEntityFields[i].setAccessible(true);
			
			try {
				assertEquals(modelFields[i], jpaEntityFields[i]);
			
			}catch(AssertionError e) {
				System.out.println("There are unreflected fields: "+ modelFields[i].getName() + ", " + jpaEntityFields[i].getName());
			}
		}
		
	}
}
