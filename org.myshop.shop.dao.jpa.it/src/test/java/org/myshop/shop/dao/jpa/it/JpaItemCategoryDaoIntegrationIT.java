package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaItemCategoryDao;
import org.myshop.shop.model.ItemCategory;

public class JpaItemCategoryDaoIntegrationIT {

	private JpaItemCategoryDao itemCategoryDao;
	private EntityManagerFactory factory;
	
	
	@Before
	public void setup() {	
		factory = Persistence.createEntityManagerFactory("myshopDB");
		itemCategoryDao = new JpaItemCategoryDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
	}
	
	@Test
	public void testRead() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id_1");
		category.setName("test_category_name_1");
		category.setDescription("test_category_descrription_1");
		
		itemCategoryDao.create(category);
		
		category = new ItemCategory();
		
		category.setId("test_category_id_2");
		category.setName("test_category_name_2");
		category.setDescription("test_category_descrription_2");
		
		itemCategoryDao.create(category);
		
		List<ItemCategory> readList = itemCategoryDao.read();
		
		assertNotNull(readList);
		
	}
	
	@Test
	public void testGet() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ItemCategory categoryToGet = itemCategoryDao.get("test_category_id");
		
		assertNotNull(categoryToGet);
		
		assertEquals(categoryToGet.getId(), "test_category_id");
		assertEquals(categoryToGet.getName(), "test_category_name");
		assertEquals(categoryToGet.getDescription(), "test_category_descrription");
		
	}
	
	@Test
	public void testUpdate() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id_update");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		category.setName("test_category_name_update");
		category.setDescription("test_category_descrription_update");
		
		category = itemCategoryDao.update(category);
		
		category = itemCategoryDao.get("test_category_id_update");
		
		assertEquals(category.getName(), "test_category_name_update");
		assertEquals(category.getDescription(), "test_category_descrription_update");
	}
	
	@Test
	public void testDelete() {
		
		this.setup();
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id_delete");
		category.setName("test_category");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		itemCategoryDao.delete(category);
		
		category = itemCategoryDao.get("test_category_id_delete");
		
		assertNull(category);
	}
	
}
