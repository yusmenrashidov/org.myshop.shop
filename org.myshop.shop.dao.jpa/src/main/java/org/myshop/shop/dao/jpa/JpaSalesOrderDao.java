package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.jpa.model.CustomerEntity;
import org.org.myshop.shop.jpa.model.SalesOrderEntity;

public class JpaSalesOrderDao implements SalesOrderDao{
	
	private EntityManager entityManager;
	private EntityManagerFactory factory;

	protected final static String READ_QUERY = "SELECT * FROM salesOrder";
	
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

	public List<SalesOrder> read() {
		
		List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
		
		@SuppressWarnings("unchecked")
		List<SalesOrderEntity> entityList = entityManager.createNativeQuery(READ_QUERY, SalesOrderEntity.class).getResultList();
		
		for(int i=0; i<entityList.size(); i++) {
			salesOrderList.add(entityList.get(i).toSalesOrder());
		}
		
		return salesOrderList;
	}

	public SalesOrder get(String id) {
		
		try {
		SalesOrderEntity entity = entityManager.find(SalesOrderEntity.class, id);
		return entity.toSalesOrder();
		
		}catch(NullPointerException e) {
			return null;
		}
	}

	public SalesOrder update(SalesOrder order) {
		
		entityManager = factory.createEntityManager();
				
		SalesOrderEntity entity = entityManager.find(SalesOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entity.setCustomer(new CustomerEntity(order.getCustomer()));
		entity.setCreated(order.getCreated());
		entityManager.getTransaction().commit();
		
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
