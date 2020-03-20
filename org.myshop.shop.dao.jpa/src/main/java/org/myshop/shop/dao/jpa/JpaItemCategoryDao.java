package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.jpa.model.ItemCategoryEntity;

import static java.util.stream.Collectors.toList;

public class JpaItemCategoryDao implements ItemCategoryDao{

	public final static String READ_QUERY_NAME = "itemCategory.read";

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public JpaItemCategoryDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(ItemCategory category) {
		entityManager.getTransaction().begin();
		entityManager.persist(new ItemCategoryEntity(category));
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<ItemCategory> read() {
		List<ItemCategoryEntity> entityList;
		
		try {
		 entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();

		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
					     .map(itemCategoryEntity -> itemCategoryEntity.toItemCategory())
				         .collect(toList());
	}

	public ItemCategory get(String id) {
		try {
			return entityManager.find(ItemCategoryEntity.class, id).toItemCategory();
		}catch(NullPointerException e) {
		return null;
		}
	}

	public ItemCategory update(ItemCategory category) {
		
		entityManager = factory.createEntityManager();
		
		ItemCategoryEntity entity = entityManager.find(ItemCategoryEntity.class, category.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setDescription(category.getDescription());
		entity.setName(category.getName());
		entityManager.getTransaction().commit();
		}catch(Exception e) {
			return null;
		}
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
