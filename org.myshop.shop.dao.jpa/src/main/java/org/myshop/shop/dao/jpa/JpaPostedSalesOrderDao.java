package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.PostedSalesOrderDao;
import org.myshop.shop.model.PostedSalesOrder;

import org.org.myshop.shop.jpa.model.CustomerEntity;
import org.org.myshop.shop.jpa.model.PostedSalesOrderEntity;

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

	public List<PostedSalesOrder> read() {
		
		List<PostedSalesOrder> salesOrderList = new ArrayList<PostedSalesOrder>();
		
		@SuppressWarnings("unchecked")
		List<PostedSalesOrderEntity> entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		for(int i=0; i<entityList.size(); i++) {
			salesOrderList.add(entityList.get(i).toPostedSalesOrder());
		}
		
		return salesOrderList;
	}

	public PostedSalesOrder get(String id) {
		
		try {
			PostedSalesOrderEntity entity = entityManager.find(PostedSalesOrderEntity.class, id);
		return entity.toPostedSalesOrder();
		
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
