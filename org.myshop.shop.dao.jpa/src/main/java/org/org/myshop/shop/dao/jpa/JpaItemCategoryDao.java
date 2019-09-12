package org.org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.jpa.model.ItemCategoryEntity;

public class JpaItemCategoryDao implements ItemCategoryDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	protected final static String READ_QUERY = "SELECT * FROM itemCategory";
	
	public JpaItemCategoryDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(ItemCategory category) {

		ItemCategoryEntity entity = new ItemCategoryEntity(category);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		
	}

	public List<ItemCategory> read() {
		
		List<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();
		
		@SuppressWarnings("unchecked")
		List<ItemCategoryEntity> entityList = entityManager.createNativeQuery(READ_QUERY, ItemCategoryEntity.class).getResultList();
		
		for(int i=0; i < entityList.size(); i++) {
			itemCategoryList.add(entityList.get(i).toItemCategory());
		}
		
		return itemCategoryList;
	}

	public ItemCategory get(String id) {
		
		try {
		ItemCategoryEntity entity = entityManager.find(ItemCategoryEntity.class, id);
		return entity.toItemCategory();
		
		}catch(NullPointerException e) {		
		return null;
		}
	}

	public ItemCategory update(ItemCategory category) {
		
		entityManager = factory.createEntityManager();
		
		ItemCategoryEntity entity = entityManager.find(ItemCategoryEntity.class, category.getId());
		
		entityManager.getTransaction().begin();
		entity.setDescription(category.getDescription());
		entity.setName(category.getName());
		entityManager.getTransaction().commit();
		
		entity = entityManager.find(ItemCategoryEntity.class, category.getId());
		
		return entity.toItemCategory();
	}

	public void delete(ItemCategory category) {
		
		entityManager = factory.createEntityManager();
		
		ItemCategoryEntity entity = entityManager.find(ItemCategoryEntity.class, category.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		
	}

}
