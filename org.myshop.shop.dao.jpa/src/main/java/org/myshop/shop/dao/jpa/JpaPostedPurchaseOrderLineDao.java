package org.myshop.shop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.PostedPurchaseOrderLineDao;
import org.myshop.shop.model.PostedPurchaseOrderLine;

import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.PostedPurchaseOrderLineEntity;

import static java.util.stream.Collectors.*;

public class JpaPostedPurchaseOrderLineDao implements PostedPurchaseOrderLineDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "postedPurchaseOrderLine.read";
	
	public JpaPostedPurchaseOrderLineDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(PostedPurchaseOrderLine line) {
		PostedPurchaseOrderLineEntity entity = new PostedPurchaseOrderLineEntity(line);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<PostedPurchaseOrderLine> read() {
		List<PostedPurchaseOrderLineEntity> entityList;
		
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						.map(postedPurchaseOrderLineEntity -> postedPurchaseOrderLineEntity.toPostedPurchaseOrderLine())
						.collect(toList());
	}

	public PostedPurchaseOrderLine get(String id) {
	
		try {
			return entityManager.find(PostedPurchaseOrderLineEntity.class, id).toPostedPurchaseOrderLine();
		}catch(NullPointerException e) {
		return null;
		}
	}

	public PostedPurchaseOrderLine update(PostedPurchaseOrderLine line) {
		entityManager = factory.createEntityManager();
		
		PostedPurchaseOrderLineEntity entity = entityManager.find(PostedPurchaseOrderLineEntity.class, line.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setItem(new ItemEntity(line.getItem()));
		entity.setLineNumber(line.getLineNumber());
		entity.setPrice(line.getPrice());
		entity.setQuantity(line.getQuantity());
		entity.setPrice(line.getPrice());
		entity.setAmmount(line.getAmount());
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
		entity = entityManager.find(PostedPurchaseOrderLineEntity.class, line.getId());
		
		return entity.toPostedPurchaseOrderLine();
	}

	public void delete(PostedPurchaseOrderLine line) {
		entityManager = factory.createEntityManager();
		
		PostedPurchaseOrderLineEntity entity = entityManager.find(PostedPurchaseOrderLineEntity.class, line.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
