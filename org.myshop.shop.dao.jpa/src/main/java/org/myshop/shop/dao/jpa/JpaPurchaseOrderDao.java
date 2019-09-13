package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;

import org.org.myshop.shop.jpa.model.PurchaseOrderEntity;

public class JpaPurchaseOrderDao implements PurchaseOrderDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	protected final static String READ_QUERY = "SELECT * FROM purchaseOrder";
	
	public JpaPurchaseOrderDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(PurchaseOrder order) {
		
		PurchaseOrderEntity entity = new PurchaseOrderEntity(order);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	public List<PurchaseOrder> read() {
		
		List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
		
		@SuppressWarnings("unchecked")
		List<PurchaseOrderEntity> entityList = entityManager.createNativeQuery(READ_QUERY, PurchaseOrderEntity.class).getResultList();
		
		for(int i=0; i<entityList.size(); i++) {
			purchaseOrderList.add(entityList.get(i).toPurchaseOrder());
		}
		
		return purchaseOrderList;
	}

	public PurchaseOrder get(String id) {
		
		try {
			PurchaseOrderEntity entity = entityManager.find(PurchaseOrderEntity.class, id);
			return entity.toPurchaseOrder();
		
		}catch(NullPointerException e) {
		return null;
		}
	}

	public PurchaseOrder update(PurchaseOrder order) {
		
		entityManager = factory.createEntityManager();
		
		PurchaseOrderEntity entity = entityManager.find(PurchaseOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entity.setNumber(order.getNumber());
		entity.setCreated(order.getCreated());
		entityManager.getTransaction().commit();
		
		entity = entityManager.find(PurchaseOrderEntity.class, order.getId());
		
		return entity.toPurchaseOrder();
	}

	public void delete(PurchaseOrder order) {
		
		entityManager = factory.createEntityManager();
		
		PurchaseOrderEntity entity = entityManager.find(PurchaseOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
