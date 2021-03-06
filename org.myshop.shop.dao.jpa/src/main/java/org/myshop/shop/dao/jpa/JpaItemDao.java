package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;

import static java.util.stream.Collectors.*;

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
	
	@SuppressWarnings("unchecked")
	public List<Item> read() {
		List<ItemEntity> entityList;
		
		try {
		 entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		}catch(PersistenceException e) {
			return null;
		}
		return entityList.stream()
					     .map(itemEntity -> itemEntity.toItem())
						 .collect(toList());
	}

	public Item get(String id) {
		try{
			return entityManager.find(ItemEntity.class, id).toItem();
		}catch(NullPointerException e) {
			return null;
		}
	}

	public Item update(Item item) {
		entityManager = factory.createEntityManager();
		
		ItemEntity itemEntity = entityManager.find(ItemEntity.class, item.getId());
		
		try {
		entityManager.getTransaction().begin();
		itemEntity.setName(item.getName());
		itemEntity.setDescription(item.getDescription());
		itemEntity.setProductGroup(new ProductGroupEntity(item.getProductGroup()));
		itemEntity.setItemCategory(new ItemCategoryEntity(item.getItemCategory()));
		itemEntity.setPurchasePrice(item.getPurchasePrice());
		itemEntity.setSalesPrice(item.getSalesPrice());
		
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
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
