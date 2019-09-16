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
import org.myshop.shop.dao.jpa.JpaItemDao;
import org.myshop.shop.dao.jpa.JpaProductGroupDao;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public class JpaItemDaoIntegrationITT {

	private JpaProductGroupDao productGroupDao;
	private JpaItemCategoryDao itemCategoryDao;
	private JpaItemDao itemDao;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() {
		factory = Persistence.createEntityManagerFactory("myshopDB");
		productGroupDao = new JpaProductGroupDao(factory);
		itemCategoryDao = new JpaItemCategoryDao(factory);
		itemDao = new JpaItemDao(factory);
	}
	
	@After
	public void cleanup() {
		List<Item> itemList = itemDao.read();
		for (Item item : itemList) {
			itemDao.delete(item);
		}
		
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
		
		category.setId("item_category_id");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup productGroup = new ProductGroup();
		
		productGroup.setId("item_product_id");
		productGroup.setDescription("product_description");
		productGroup.setItemCategory(category);
		
		productGroupDao.create(productGroup);
		
		Item item = new Item();
		
		item.setId("test_item_id");
		item.setDescription("test_item_descrpiption");
		item.setItemCategory(category);
		item.setProductGroup(productGroup);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		itemDao.create(item);
		
	}
	
	@Test
	public void testRead() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("item_category_id_1");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		category = new ItemCategory();
		category.setId("item_category_id_2");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("item_product_id_2");
		group.setDescription("item_product_description");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		Item item = new Item();
		
		item.setId("item_id_1");
		item.setDescription("test_item_descpription");
		item.setProductGroup(group);
		item.setItemCategory(category);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		itemDao.create(item);
		
		item = new Item();
		item.setId("item_id_2");
		item.setDescription("test_item_descpription");
		item.setProductGroup(group);
		item.setItemCategory(category);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		itemDao.create(item);
		
		List<Item> itemList = itemDao.read();
		
		assertNotNull(itemList);
	}
	
	
	@Test
	public void testGet() {

		ItemCategory category = new ItemCategory();
		
		category.setId("test_category_id_get");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("test_item_product_id_get");
		group.setDescription("product_description_get");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		Item item = new Item();
		
		item.setId("item_id_get");
		item.setDescription("test_item_descpription_get");
		item.setProductGroup(group);
		item.setItemCategory(category);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		itemDao.create(item);
		
		item = itemDao.get("item_id_get");
		
		assertNotNull(item);
		
		assertEquals(item.getId(), "item_id_get");
		assertEquals(item.getDescription(), "test_item_descpription_get");
		assertEquals(item.getItemCategory().getId(), "test_category_id_get");
		assertEquals(item.getProductGroup().getId(), "test_item_product_id_get");
	}
	
	@Test
	public void testUpdate() {
		
		ItemCategory category = new ItemCategory();
		
		category.setId("item_itemCategory_id_update");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("item_productGroup_id_update");
		group.setDescription("product_description");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		
		Item item = new Item();
		
		item.setId("item_id_update");
		item.setDescription("test_item_descpription");
		item.setProductGroup(group);
		item.setItemCategory(category);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		itemDao.create(item);
		
		item.setDescription("test_item_descpription_update");
		item.setPurchasePrice(123.123f);
		item.setSalesPrice(123.321f);
		
		item = itemDao.update(item);
		
		assertEquals(item.getDescription(), "test_item_descpription_update");
		assertEquals(0f, item.getPurchasePrice(), 123.123f);
		assertEquals(0f, item.getSalesPrice(), 123.321f);	
	}
	
	@Test
	public void testDelete() {

		ItemCategory category = new ItemCategory();
		
		category.setId("item_itemCategory_id_delete");
		category.setName("test_category_name");
		category.setDescription("test_category_descrription");
		
		itemCategoryDao.create(category);
		
		ProductGroup group = new ProductGroup();
		
		group.setId("item_productGroup_id_delete");
		group.setDescription("product_description");
		group.setItemCategory(category);
		
		productGroupDao.create(group);
		

		Item item = new Item();
		
		item.setId("item_id_delete");
		item.setDescription("test_item_descpription");
		item.setProductGroup(group);
		item.setItemCategory(category);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		itemDao.create(item);
		
		itemDao.delete(item);
		
		item = itemDao.get("item_id_delete");
		
		assertNull(item);
		
	}
}
