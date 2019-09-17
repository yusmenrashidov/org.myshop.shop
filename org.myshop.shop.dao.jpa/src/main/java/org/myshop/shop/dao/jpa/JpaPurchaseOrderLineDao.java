package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.PurchaseOrderLineDao;
import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.PurchaseOrderLineEntity;

public class JpaPurchaseOrderLineDao implements PurchaseOrderLineDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "purchaseOrderLine.read";
	
	public JpaPurchaseOrderLineDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(PurchaseOrderLine line) {
		
		PurchaseOrderLineEntity entity = new PurchaseOrderLineEntity(line);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<PurchaseOrderLine> read() {
		
		List<PurchaseOrderLine> lineList = new ArrayList<PurchaseOrderLine>();	
		List<PurchaseOrderLineEntity> entityList;
		
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		}catch(PersistenceException e) {
			return null;
		}
		
		for(int i=0; i<entityList.size(); i++) {
			lineList.add(entityList.get(i).toPurchaseOrderLine());
		}
		
		return lineList;
	}

	public PurchaseOrderLine get(String id) {
	
		try {
		PurchaseOrderLineEntity entity = entityManager.find(PurchaseOrderLineEntity.class, id);
		return entity.toPurchaseOrderLine();
		
		}catch(NullPointerException e) {
		return null;
		}
	}

	public PurchaseOrderLine update(PurchaseOrderLine line) {
		
		entityManager = factory.createEntityManager();
		
		PurchaseOrderLineEntity entity = entityManager.find(PurchaseOrderLineEntity.class, line.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setItem(new ItemEntity(line.getItem()));
		entity.setLineNumber(line.getLineNumber());
		entity.setPrice(line.getPrice());
		entity.setQuantity(line.getQuantity());
		entity.setPrice(line.getPrice());
		entity.setAmmount(line.getAmmount());
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
		
		entity = entityManager.find(PurchaseOrderLineEntity.class, line.getId());
		
		return entity.toPurchaseOrderLine();
	}

	public void delete(PurchaseOrderLine line) {
		
		entityManager = factory.createEntityManager();
		
		PurchaseOrderLineEntity entity = entityManager.find(PurchaseOrderLineEntity.class, line.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
