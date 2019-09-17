package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;

public class JpaItemDao implements ItemDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "item.read";
	
	public JpaItemDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(Item item) {
		
		ItemEntity itemEntity = new ItemEntity(item);
		
		entityManager.getTransaction().begin();
		entityManager.persist(itemEntity);
		entityManager.getTransaction().commit();
	}

	public List<Item> read() {
		
		List<Item> itemList = new ArrayList<Item>();
		
		@SuppressWarnings("unchecked")
		List<ItemEntity> entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		for(int i=0; i < entityList.size(); i++) {
			itemList.add(entityList.get(i).toItem());
		}
		
		return itemList;
	}

	public Item get(String id) {
		
		try {
			ItemEntity itemEntity = entityManager.find(ItemEntity.class, id);
			return itemEntity.toItem();
			
		}catch(NullPointerException e) {
			return null;
		}
	}

	public Item update(Item item) {
		
		entityManager = factory.createEntityManager();
		
		ItemEntity itemEntity = entityManager.find(ItemEntity.class, item.getId());
		
		entityManager.getTransaction().begin();
		itemEntity.setName(item.getName());
		itemEntity.setDescription(item.getDescription());
		itemEntity.setProductGroup(new ProductGroupEntity(item.getProductGroup()));
		itemEntity.setItemCategory(new ItemCategoryEntity(item.getItemCategory()));
		itemEntity.setPurchasePrice(item.getPurchasePrice());
		itemEntity.setSalesPrice(item.getSalesPrice());
		
		entityManager.getTransaction().commit();
		
		itemEntity = entityManager.find(ItemEntity.class, item.getId());
		
		return itemEntity.toItem();
	}

	public void delete(Item item) {
		
		entityManager = factory.createEntityManager();
		
		ItemEntity itemEntity = entityManager.find(ItemEntity.class, item.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(itemEntity);
		entityManager.getTransaction().commit();
	}

}
