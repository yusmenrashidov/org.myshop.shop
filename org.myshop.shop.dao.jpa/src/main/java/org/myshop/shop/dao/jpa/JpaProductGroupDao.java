package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;

public class JpaProductGroupDao implements ProductGroupDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "productGroup.read";
	
	public JpaProductGroupDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(ProductGroup productGroup) {
	
		ProductGroupEntity entity = new ProductGroupEntity(productGroup);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();	
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductGroup> read() {
		
		List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
		List<ProductGroupEntity> entityList;
		
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		}catch(PersistenceException e) {
			return null;
		}
		
		for(int i=0; i<entityList.size(); i++) {
			productGroupList.add(entityList.get(i).toProductGroup());
		}
		
		return productGroupList;
	}

	public ProductGroup get(String id) {
		
		try {
		ProductGroupEntity entity = entityManager.find(ProductGroupEntity.class, id);
		return entity.toProductGroup();
		
		}catch(NullPointerException e) {
		return null;
		}
	}

	public ProductGroup update(ProductGroup productGroup) {
		
		entityManager = factory.createEntityManager();
		
		ProductGroupEntity entity = entityManager.find(ProductGroupEntity.class, productGroup.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setDescription(productGroup.getDescription());
		entity.setItemCategory(new ItemCategoryEntity(productGroup.getItemCategory()));
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
		
		entity = entityManager.find(ProductGroupEntity.class, productGroup.getId());
		
		return entity.toProductGroup();
	}

	public void delete(ProductGroup productGroup) {
		
		entityManager = factory.createEntityManager();
		
		ProductGroupEntity entity = entityManager.find(ProductGroupEntity.class, productGroup.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}

}
