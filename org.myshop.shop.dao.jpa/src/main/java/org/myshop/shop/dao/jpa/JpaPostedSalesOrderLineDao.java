package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.PostedSalesOrderLineDao;
import org.myshop.shop.model.PostedSalesOrderLine;

import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.PostedSalesOrderLineEntity;

public class JpaPostedSalesOrderLineDao implements PostedSalesOrderLineDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "postedSalesOrderLine.read";
	
	public JpaPostedSalesOrderLineDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(PostedSalesOrderLine line) {
		
		PostedSalesOrderLineEntity entity = new PostedSalesOrderLineEntity(line);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	public List<PostedSalesOrderLine> read() {
		
		List<PostedSalesOrderLine> lineList = new ArrayList<PostedSalesOrderLine>();
		
		@SuppressWarnings("unchecked")
		List<PostedSalesOrderLineEntity> entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		for(int i=0; i<entityList.size(); i++) {
			lineList.add(entityList.get(i).toPostedSalesOrderLine());
		}
		
		return lineList;
	}

	public PostedSalesOrderLine get(String id) {
		
		try {
			PostedSalesOrderLineEntity entity = entityManager.find(PostedSalesOrderLineEntity.class, id);
		return entity.toPostedSalesOrderLine();
		
		}catch(NullPointerException e) {
			return null;
		}
		
	}

	public PostedSalesOrderLine update(PostedSalesOrderLine line) {
		
		entityManager = factory.createEntityManager();
		
		PostedSalesOrderLineEntity entity = entityManager.find(PostedSalesOrderLineEntity.class, line.getId());
		
		entityManager.getTransaction().begin();
		entity.setItem(new ItemEntity(line.getItem()));
		entity.setAmmount(line.getAmmount());
		entity.setLineNumber(line.getLineNumber());
		entity.setPrice(line.getPrice());
		entity.setQuantity(line.getLineNumber());
		entityManager.getTransaction().commit();
		
		entity = entityManager.find(PostedSalesOrderLineEntity.class, line.getId());
		
		return entity.toPostedSalesOrderLine();
	}

	public void delete(PostedSalesOrderLine line) {
		
		PostedSalesOrderLineEntity entity = entityManager.find(PostedSalesOrderLineEntity.class, line.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
