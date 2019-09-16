package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaItemCategoryDao;
import org.myshop.shop.dao.jpa.JpaProductGroupDao;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public class JpaProductGroupDaoIntegrationIT {

	private JpaProductGroupDao productGroupDao;
	private JpaItemCategoryDao itemCategoryDao;
	private EntityManagerFactory factory;

	
	@Before
	public void setup() {
		factory = Persistence.createEntityManagerFactory("myshopDB");
		productGroupDao = new JpaProductGroupDao(factory);
		itemCategoryDao = new JpaItemCategoryDao(factory);
	}
	
	@After
	public void cleanup() {
		List<ProductGroup> productGroupList = productGroupDao.read();
		for (ProductGroup productGroup : productGroupList) {
			productGroupDao.delete(productGroup);
		}
		
		List<ItemCategory> itemCategoryList = itemCategoryDao.read();
		for (ItemCategory itemCategory : itemCategoryList) {
			itemCategoryDao.delete(itemCategory);
		}
	}
	
	@Test
	public void testCreate() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_item_category_id");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("product_id");
		group.setDescription("product_description");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
	}
	
	@Test
	public void testRead() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_item_category_id_1");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		category = new ItemCategory();
		category.setId("test_item_category_id_2");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("product_id_1");
		group.setDescription("product_description");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		group = new ProductGroup();
		group.setId("product_id_2");
		group.setDescription("product_description");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		List<ProductGroup> groupList = productGroupDao.read();
		
		assertNotNull(groupList);
		
	}
	
	@Test
	public void testGet() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("test_item_category_id_get");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("product_id_get");
		group.setDescription("product_description_get");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		group = productGroupDao.get("product_id_get");
		
		assertNotNull(group);
		assertEquals(group.getId(), "product_id_get");
		assertEquals(group.getDescription(), "product_description_get");
		assertEquals(group.getItemCategory().getId(), "test_item_category_id_get");
	}
	
	@Test
	public void testUpdate() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("item_category_id_update");
		category.setName("category_name");
		category.setDescription("category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("product_id_update");
		group.setDescription("product_description_update");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
	
		group.setDescription("product_description_new");
	
		group.setItemCategory(category);
		
		group = productGroupDao.update(group);
		
		assertNotNull(group);
		assertEquals(group.getId(), "product_id_update");
		assertEquals(group.getDescription(), "product_description_new");
		assertEquals(group.getItemCategory().getId(), "item_category_id_update");
	}
	
	@Test
	public void testDelete() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("item_category_id_delete");
		category.setName("category_name");
		category.setDescription("category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("group_id_delete");
		group.setDescription("group_description_delete");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		productGroupDao.delete(group);
		
		group = productGroupDao.get("group_id_delete");
		
		assertNull(group);
	}
}
