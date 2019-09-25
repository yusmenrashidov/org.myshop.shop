package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public class ProductGroupEntityTest {

	@Test
	public void testCreateProductGroupEntity() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id");
		category.setDescription("test_category_description");
		category.setName("test_category_name");
		
		ProductGroup group = new ProductGroup();
		
		group.setId("test_group_id");
		group.setItemCategory(category);
		group.setDescription("test_group_description");
		
		ProductGroupEntity entity = new ProductGroupEntity(group);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), group.getId());
		assertEquals(entity.getDescription(), group.getDescription());
		assertEquals(entity.getItemCategory().getId(), group.getItemCategory().getId());
		
	}
	
	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = ProductGroup.class.getFields();
		Field [] jpaEntityFields = ProductGroupEntity.class.getFields();
		
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
