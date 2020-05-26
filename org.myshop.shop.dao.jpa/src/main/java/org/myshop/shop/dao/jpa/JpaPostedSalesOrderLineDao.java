package org.myshop.shop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.PostedSalesOrderLineDao;
import org.myshop.shop.model.PostedSalesOrderLine;

import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.PostedSalesOrderLineEntity;

import static java.util.stream.Collectors.*;

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

	@SuppressWarnings("unchecked")
	public List<PostedSalesOrderLine> read() {
		List<PostedSalesOrderLineEntity> entityList;
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						 .map(postedSalesOrderLineEntity -> postedSalesOrderLineEntity.toPostedSalesOrderLine())
						 .collect(toList());
	}

	public PostedSalesOrderLine get(String id) {
		try {
		return entityManager.find(PostedSalesOrderLineEntity.class, id).toPostedSalesOrderLine();
		}catch(NullPointerException e) {
			return null;
		}
	}

	public PostedSalesOrderLine update(PostedSalesOrderLine line) {
		entityManager = factory.createEntityManager();
		
		PostedSalesOrderLineEntity entity = entityManager.find(PostedSalesOrderLineEntity.class, line.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setItem(new ItemEntity(line.getItem()));
		entity.setAmmount(line.getAmount());
		entity.setLineNumber(line.getLineNumber());
		entity.setPrice(line.getPrice());
		entity.setQuantity(line.getLineNumber());
		entityManager.getTransaction().commit();
	
		}catch(Exception e) {
			return null;
		}
		
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
