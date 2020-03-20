package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;

import org.org.myshop.shop.jpa.model.PostedPurchaseOrderEntity;

import static java.util.stream.Collectors.*;

public class JpaPostedPurchaseOrderDao implements PostedPurchaseOrderDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "postedPurchaseOrder.read";
	
	public JpaPostedPurchaseOrderDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(PostedPurchaseOrder order) {
		PostedPurchaseOrderEntity entity = new PostedPurchaseOrderEntity(order);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<PostedPurchaseOrder> read() {
		List<PostedPurchaseOrderEntity> entityList;
		
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						 .map(postedPurchaseOrderEntity -> postedPurchaseOrderEntity.toPostedPurchaseOrder())
						 .collect(toList());
	}

	public PostedPurchaseOrder get(String id) {
		try {
			return entityManager.find(PostedPurchaseOrderEntity.class, id).toPostedPurchaseOrder();
		}catch(NullPointerException e) {
		return null;
		}
	}

	public PostedPurchaseOrder update(PostedPurchaseOrder order) {
		entityManager = factory.createEntityManager();
		PostedPurchaseOrderEntity entity = entityManager.find(PostedPurchaseOrderEntity.class, order.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setNumber(order.getNumber());
		entity.setCreated(order.getCreated());
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
		
		entity = entityManager.find(PostedPurchaseOrderEntity.class, order.getId());
		
		return entity.toPostedPurchaseOrder();
	}

	public void delete(PostedPurchaseOrder order) {
		entityManager = factory.createEntityManager();
		
		PostedPurchaseOrderEntity entity = entityManager.find(PostedPurchaseOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
