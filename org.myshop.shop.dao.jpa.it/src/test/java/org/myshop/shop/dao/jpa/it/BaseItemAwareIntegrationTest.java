package org.myshop.shop.dao.jpa.it;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.dao.jpa.JpaItemCategoryDao;
import org.myshop.shop.dao.jpa.JpaItemDao;
import org.myshop.shop.dao.jpa.JpaProductGroupDao;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public class BaseItemAwareIntegrationTest {

	protected static final String TEST_ITEM_CATEGORY_ID = "test_item_category_id";
	protected static final String TEST_ITEM_CATEGORY_NAME = "test_item_category_name";
	protected static final String TEST_ITEM_CATEGORY_DESCRIPTION = "test_item_category_description";

	protected static final String TEST_PRODUCT_GROUP_ID = "test_product_group_id";
	protected static final String TEST_PRODUCT_GROUP_DESCRIPTION = "test_product_group_description";

	protected static final String TEST_ITEM_ID = "test_item_id";
	protected static final String TEST_ITEM_NAME = "test_item_name";
	protected static final String TEST_ITEM_DESCRIPTION = "test_item_description";
	protected static final float TEST_ITEM_SALES_PRICE = 123;
	protected static final float TEST_ITEM_PURCHASE_PRICE = 234;

	protected static final String TEST_ITEM_ID_2 = "test_item_id_2";
	protected static final String TEST_ITEM_NAME_2 = "test_item_name_2";
	protected static final String TEST_ITEM_DESCRIPTION_2 = "test_item_description_2";
	protected static final float TEST_ITEM_SALES_PRICE_2 = 345;
	protected static final float TEST_ITEM_PURCHASE_PRICE_2 = 456;

	protected EntityManagerFactory factory;

	protected ItemDao itemDao;
	protected ItemCategoryDao itemCategoryDao;
	protected ProductGroupDao productGroupDao;

	public void setup() {
		factory = Persistence.createEntityManagerFactory("myshopDB");

		itemDao = new JpaItemDao(factory);
		itemCategoryDao = new JpaItemCategoryDao(factory);
		productGroupDao = new JpaProductGroupDao(factory);

		initTestItem();
	}

	@After
	public void cleanup() {
		System.out.println("super cleanup, start");

		clearTestItem();
		
		System.out.println("super cleanup, end");
	}

	private void initTestItem() {
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setId(TEST_ITEM_CATEGORY_ID);
		itemCategory.setName(TEST_ITEM_CATEGORY_NAME);
		itemCategory.setDescription(TEST_ITEM_CATEGORY_DESCRIPTION);
		itemCategoryDao.create(itemCategory);

		ProductGroup productGroup = new ProductGroup();
		productGroup.setId(TEST_PRODUCT_GROUP_ID);
		productGroup.setDescription(TEST_PRODUCT_GROUP_DESCRIPTION);
		productGroup.setItemCategory(itemCategory);
		productGroupDao.create(productGroup);

		Item item = new Item();
		item.setId(TEST_ITEM_ID);
		item.setName(TEST_ITEM_NAME);
		item.setDescription(TEST_ITEM_DESCRIPTION);
		item.setItemCategory(itemCategory);
		item.setProductGroup(productGroup);
		item.setSalesPrice(TEST_ITEM_SALES_PRICE);
		item.setPurchasePrice(TEST_ITEM_PURCHASE_PRICE);
		itemDao.create(item);

		item = new Item();
		item.setId(TEST_ITEM_ID_2);
		item.setName(TEST_ITEM_NAME_2);
		item.setDescription(TEST_ITEM_DESCRIPTION_2);
		item.setItemCategory(itemCategory);
		item.setProductGroup(productGroup);
		item.setSalesPrice(TEST_ITEM_SALES_PRICE_2);
		item.setPurchasePrice(TEST_ITEM_PURCHASE_PRICE_2);
		itemDao.create(item);
	}

	private void clearTestItem() {
		List<Item> itemList = itemDao.read();
		
		System.out.println(itemList);
		
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
}
