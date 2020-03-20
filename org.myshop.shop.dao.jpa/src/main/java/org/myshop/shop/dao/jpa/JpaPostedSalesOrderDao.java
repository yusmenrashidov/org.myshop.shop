package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.PostedSalesOrderDao;
import org.myshop.shop.model.PostedSalesOrder;

import org.org.myshop.shop.jpa.model.CustomerEntity;
import org.org.myshop.shop.jpa.model.PostedSalesOrderEntity;

import static java.util.stream.Collectors.*;

public class JpaPostedSalesOrderDao implements PostedSalesOrderDao{
	
	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "postedSalesOrder.read";

	public JpaPostedSalesOrderDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(PostedSalesOrder order) {
		PostedSalesOrderEntity entity = new PostedSalesOrderEntity(order);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<PostedSalesOrder> read() {
		List<PostedSalesOrderEntity> entityList;
		
		try {
			entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						 .map(postedSalesOrderEntity -> postedSalesOrderEntity.toPostedSalesOrder())
						 .collect(toList());
	}

	public PostedSalesOrder get(String id) {
		
		try {
			return entityManager.find(PostedSalesOrderEntity.class, id).toPostedSalesOrder();
		}catch(NullPointerException e) {
			return null;
		}
	}

	public PostedSalesOrder update(PostedSalesOrder order) {
		entityManager = factory.createEntityManager();
				
		PostedSalesOrderEntity entity = entityManager.find(PostedSalesOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entity.setCustomer(new CustomerEntity(order.getCustomer()));
		entity.setCreated(order.getCreated());
		entityManager.getTransaction().commit();
		
		entity = entityManager.find(PostedSalesOrderEntity.class, order.getId());
		
		return entity.toPostedSalesOrder();
	}

	public void delete(PostedSalesOrder order) {
		entityManager = factory.createEntityManager();
		
		PostedSalesOrderEntity entity = entityManager.find(PostedSalesOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
