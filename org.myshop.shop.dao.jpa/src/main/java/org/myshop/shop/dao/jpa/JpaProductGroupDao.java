package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.jpa.model.ItemCategoryEntity;
import org.org.myshop.shop.jpa.model.ProductGroupEntity;

public class JpaProductGroupDao implements ProductGroupDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	protected final static String READ_QUERY = "SELECT * FROM productGroup";
	
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

	public List<ProductGroup> read() {
		
		List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
		
		@SuppressWarnings("unchecked")
		List<ProductGroupEntity> entityList = entityManager.createNativeQuery(READ_QUERY, ProductGroupEntity.class).getResultList();
		
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
		
		entityManager.getTransaction().begin();
		entity.setDescription(productGroup.getDescription());
		entity.setItemCategory(new ItemCategoryEntity(productGroup.getItemCategory()));
		entityManager.getTransaction().commit();
		
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
