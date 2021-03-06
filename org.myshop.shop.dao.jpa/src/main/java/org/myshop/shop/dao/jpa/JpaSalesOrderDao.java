package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.jpa.model.CustomerEntity;
import org.org.myshop.shop.jpa.model.SalesOrderEntity;

public class JpaSalesOrderDao implements SalesOrderDao{
	
	private EntityManager entityManager;
	private EntityManagerFactory factory;

	public final static String READ_QUERY_NAME = "salesOrder.read";
	
	public JpaSalesOrderDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(SalesOrder order) {
		SalesOrderEntity entity = new SalesOrderEntity(order);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesOrder> read() {
		List<SalesOrderEntity> entityList;
		
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						.map(salesOrderEntity -> salesOrderEntity.toSalesOrder())
						.collect(Collectors.toList());
	}

	public SalesOrder get(String id) {
		try {
		return entityManager.find(SalesOrderEntity.class, id).toSalesOrder();
		}catch(NullPointerException e) {
			return null;
		}
	}

	public SalesOrder update(SalesOrder order) {
		entityManager = factory.createEntityManager();
				
		SalesOrderEntity entity = entityManager.find(SalesOrderEntity.class, order.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setCustomer(new CustomerEntity(order.getCustomer()));
		entity.setCreated(order.getCreated());
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
		
		entity = entityManager.find(SalesOrderEntity.class, order.getId());
		
		return entity.toSalesOrder();
	}

	public void delete(SalesOrder order) {
		entityManager = factory.createEntityManager();
		
		SalesOrderEntity entity = entityManager.find(SalesOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
