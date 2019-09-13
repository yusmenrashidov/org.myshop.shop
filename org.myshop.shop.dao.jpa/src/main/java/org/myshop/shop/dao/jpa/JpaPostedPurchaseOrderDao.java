package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;

import org.org.myshop.shop.jpa.model.PostedPurchaseOrderEntity;

public class JpaPostedPurchaseOrderDao implements PostedPurchaseOrderDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	protected final static String READ_QUERY = "SELECT * FROM postedPurchaseOrder";
	
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

	public List<PostedPurchaseOrder> read() {
		
		List<PostedPurchaseOrder> purchaseOrderList = new ArrayList<PostedPurchaseOrder>();
		
		@SuppressWarnings("unchecked")
		List<PostedPurchaseOrderEntity> entityList = entityManager.createNativeQuery(READ_QUERY, PostedPurchaseOrderEntity.class).getResultList();
		
		for(int i=0; i<entityList.size(); i++) {
			purchaseOrderList.add(entityList.get(i).toPostedPurchaseOrder());
		}
		
		return purchaseOrderList;
	}

	public PostedPurchaseOrder get(String id) {
		
		try {
			PostedPurchaseOrderEntity entity = entityManager.find(PostedPurchaseOrderEntity.class, id);
			return entity.toPostedPurchaseOrder();
		
		}catch(NullPointerException e) {
		return null;
		}
	}

	public PostedPurchaseOrder update(PostedPurchaseOrder order) {
		
		entityManager = factory.createEntityManager();
		
		PostedPurchaseOrderEntity entity = entityManager.find(PostedPurchaseOrderEntity.class, order.getId());
		
		entityManager.getTransaction().begin();
		entity.setNumber(order.getNumber());
		entity.setCreated(order.getCreated());
		entityManager.getTransaction().commit();
		
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
